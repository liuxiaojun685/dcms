package cn.bestsec.dcms.platform.impl.middleware;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Middleware_UserRegisterApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Middleware_UserRegisterRequest;
import cn.bestsec.dcms.platform.api.model.Middleware_UserRegisterResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.dao.UserToDepartmentDao;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.UserToDepartment;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月1日 下午2:48:40
 */
@Component
public class MiddlerwareUserRegister implements Middleware_UserRegisterApi {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private UserToDepartmentDao userToDepartmentDao;
    @Autowired
    private DepartmentDao departmentDao;

    private String findUsableUid() {
        String uid = IdUtils.randowUid().toString();
        User user = userDao.findByPkUid(uid);
        if (user != null) {
            return findUsableUid();
        }
        return uid;
    }

    @Override
    public Middleware_UserRegisterResponse execute(Middleware_UserRegisterRequest request) throws ApiException {
        long currentTime = System.currentTimeMillis();
        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(currentTime).operation(AdminLogOp.user_create)
                .operateDescription(request.getName());

        String uid = request.getUid();
        if (uid != null && !uid.isEmpty()) {
            User user = userDao.findByPkUid(uid);
            if (user != null) {
                throw new ApiException(ErrorCode.userAlreadyExists);
            }
        } else {
            uid = findUsableUid();
        }
        // 判断该用户是否已存在
        User user = userDao.findByAccount(request.getAccount());
        if (user != null) {
            // 若该用户已删除(曾经存在), 将已删除的账号标记_deleted。
            if (user.getUserState() == UserConsts.State.deleted.getCode()) {
                user.setAccount(user.getAccount() + "_deleted");
                userDao.save(user);
            } else {
                throw new ApiException(ErrorCode.userAlreadyExists);
            }
        }

        // 获取默认密码
        SystemConfig config = systemConfigService.getSystemConfig();
        String localAuthInitPasswd = config.getLocalAuthInitPasswd();
        if (localAuthInitPasswd.isEmpty()) {
            throw new ApiException(ErrorCode.configNotCorrect);
        }
        String passwd = StringEncrypUtil.decrypt(localAuthInitPasswd);
        if (request.getPasswd() != null && !request.getPasswd().isEmpty()) {
            passwd = request.getPasswd();
        }

        // 创建新用户
        user = new User();
        user.setAdmin(null);
        user.setPkUid(uid);
        user.setAccount(request.getAccount());
        user.setName(request.getName());
        user.setGender(request.getGender());
        // if (!request.getPhone().isEmpty()
        // && !Pattern.matches("^1[3|4|5|7|8]\\d{9}$", request.getPhone())) {
        // throw new ApiException(ErrorCode.phoneFormatError);
        // }
        // user.setPhone(request.getPhone());
        // if (!request.getMail().isEmpty() && !Pattern.matches(
        // "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,5}$",
        // request.getMail())) {
        // throw new ApiException(ErrorCode.mailFormatError);
        // }
        user.setMail(request.getMail());
        user.setPosition(request.getPosition());
        user.setPasswd(StringEncrypUtil.encryptNonreversible(passwd));
        int level = 1;
        if (request.getLevel() != null) {
            level = request.getLevel();
        }
        user.setUserLevel(level);
        user.setUserState(UserConsts.State.def.getCode());
        user.setPasswdState(UserConsts.PasswdState.changed.getCode());
        user.setCreateFrom(UserConsts.CreateFrom.synCreate.getCode());
        user.setCreateTime(currentTime);
        user.setErrLoginCount(0);
        user.setUnlockTime(0L);
        user.setLastPasswdChangeTime(currentTime);
        userDao.save(user);
        // 添加用户到部门
        Department department = departmentDao.findByPkDid(request.getDid());
        if (department == null) {
            List<Department> root = departmentDao.findByRoot(1);
            if (root == null) {
                throw new ApiException(ErrorCode.departmentNotExistsRoot);
            }
            department = root.iterator().next();
        }
        UserToDepartment usetToDepartment = new UserToDepartment();
        usetToDepartment.setDepartment(department);
        usetToDepartment.setUser(user);
        userToDepartmentDao.save(usetToDepartment);

        return new Middleware_UserRegisterResponse(user.getPkUid());
    }
}
