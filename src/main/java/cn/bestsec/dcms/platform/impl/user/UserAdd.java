package cn.bestsec.dcms.platform.impl.user;

import java.util.List;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_AddApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.User_AddRequest;
import cn.bestsec.dcms.platform.api.model.User_AddResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.AdminDao;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.dao.UserToDepartmentDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.UserToDepartment;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 创建用户，只有系统管理员才可以创建用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月23日 下午5:12:58
 */
@Component
public class UserAdd implements User_AddApi {

    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private UserToDepartmentDao userToDepartmentDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private AdminDao adminDao;

    private String findUsableUid() {
        String uid = IdUtils.randowUid().toString();
        User user = userDao.findByPkUid(uid);
        if (user != null) {
            return findUsableUid();
        }
        return uid;
    }

    @Override
    @Transactional
    public User_AddResponse execute(User_AddRequest user_AddRequest) throws ApiException {
        AdminLogBuilder adminLogBuilder = user_AddRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.user_create)
                .admin(user_AddRequest.tokenWrapper().getAdmin()).operateDescription(user_AddRequest.getName());

        // 判断该用户是否已存在
        User user = userDao.findByAccount(user_AddRequest.getAccount());
        Admin admin = adminDao.findByAccountAndAdminStateNot(user_AddRequest.getAccount(),
                AdminConsts.AdminState.deleted.getCode());
        if (user != null || admin != null) {
            // 若该用户已删除(曾经存在), 将已删除的账号标记_deleted。
            if (user.getUserState() == UserConsts.State.deleted.getCode()) {
                user.setAccount(user.getAccount() + "_deleted");
                userDao.save(user);
            } else {
                throw new ApiException(ErrorCode.userAlreadyExists);
            }
        }

        long currentTime = System.currentTimeMillis();
        // 获取默认密码
        SystemConfig config = systemConfigService.getSystemConfig();
        String localAuthInitPasswd = config.getLocalAuthInitPasswd();
        if (localAuthInitPasswd.isEmpty()) {
            throw new ApiException(ErrorCode.configNotCorrect);
        }

        // 创建新用户
        user = new User();
        Token token = tokenDao.findByToken(user_AddRequest.getToken());
        user.setAdmin(token.getAdmin());
        user.setPkUid(findUsableUid());
        user.setAccount(user_AddRequest.getAccount());
        user.setName(user_AddRequest.getName());
        user.setGender(user_AddRequest.getGender());
        if (!user_AddRequest.getPhone().isEmpty()
                && !Pattern.matches("^1[3|4|5|7|8]\\d{9}$", user_AddRequest.getPhone())) {
            throw new ApiException(ErrorCode.phoneFormatError);
        }
        user.setPhone(user_AddRequest.getPhone());
        if (!user_AddRequest.getMail().isEmpty() && !Pattern.matches(
                "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,5}$",
                user_AddRequest.getMail())) {
            throw new ApiException(ErrorCode.mailFormatError);
        }
        user.setMail(user_AddRequest.getMail());
        user.setPosition(user_AddRequest.getPosition());
        user.setPasswd(StringEncrypUtil.encryptNonreversible(StringEncrypUtil.decrypt(localAuthInitPasswd)));
        int level = 1;
        if (user_AddRequest.getLevel() != null) {
            level = user_AddRequest.getLevel();
        }
        user.setUserLevel(level);
        user.setUserState(UserConsts.State.def.getCode());
        user.setPasswdState(UserConsts.PasswdState.init.getCode());
        user.setCreateFrom(UserConsts.CreateFrom.record.getCode());
        user.setCreateTime(currentTime);
        user.setErrLoginCount(0);
        user.setUnlockTime(0L);
        user.setLastPasswdChangeTime(currentTime);
        userDao.save(user);
        // 添加用户到部门
        Department department = departmentDao.findByPkDid(user_AddRequest.getDid());
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

        User_AddResponse resp = new User_AddResponse();
        resp.setUid(user.getPkUid());
        return resp;
    }

}
