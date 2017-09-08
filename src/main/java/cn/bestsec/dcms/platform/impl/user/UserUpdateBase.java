package cn.bestsec.dcms.platform.impl.user;

import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_UpdateBaseApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.User_UpdateBaseRequest;
import cn.bestsec.dcms.platform.api.model.User_UpdateBaseResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.DepartmentConsts;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.dao.UserToDepartmentDao;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.UserToDepartment;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改用户基础信息，系统管理员
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月26日 上午10:27:41
 */
@Component
public class UserUpdateBase implements User_UpdateBaseApi {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserToDepartmentDao userToDepartmentDao;
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public User_UpdateBaseResponse execute(User_UpdateBaseRequest user_UpdateBaseRequest) throws ApiException {
        // 获取需要被修改的用户
        User user = userDao.findByPkUid(user_UpdateBaseRequest.getUid());
        if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.userNotExist);
        }

        AdminLogBuilder adminLogBuilder = user_UpdateBaseRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.user_update)
                .admin(user_UpdateBaseRequest.tokenWrapper().getAdmin()).operateDescription(user.getName());

        // 系统管理员只能修改除密级的其他信息
        if (user_UpdateBaseRequest.getName() != null && !user_UpdateBaseRequest.getName().isEmpty()) {
            user.setName(user_UpdateBaseRequest.getName());
        }
        if (user_UpdateBaseRequest.getGender() != null) {
            user.setGender(user_UpdateBaseRequest.getGender());
        }
        if (user_UpdateBaseRequest.getPhone() != null && !user_UpdateBaseRequest.getPhone().isEmpty()) {
            if (!Pattern.matches("^1[3|4|5|7|8]\\d{9}$", user_UpdateBaseRequest.getPhone())) {
                throw new ApiException(ErrorCode.phoneFormatError);
            }
            user.setPhone(user_UpdateBaseRequest.getPhone());
        }
        if (user_UpdateBaseRequest.getMail() != null && !user_UpdateBaseRequest.getMail().isEmpty()) {
            if (!Pattern.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,5}$",
                    user_UpdateBaseRequest.getMail())) {
                throw new ApiException(ErrorCode.invalidArgument);
            }
            user.setMail(user_UpdateBaseRequest.getMail());
        }
        if (user_UpdateBaseRequest.getPosition() != null && !user_UpdateBaseRequest.getPosition().isEmpty()) {
            user.setPosition(user_UpdateBaseRequest.getPosition());
        }
        if (user_UpdateBaseRequest.getDid() != null && !user_UpdateBaseRequest.getDid().isEmpty()) {
            Department department = departmentDao.findByPkDid(user_UpdateBaseRequest.getDid());
            if (department == null || department.getDepartmentState() == DepartmentConsts.state.deleted.getCode()) {
                throw new ApiException(ErrorCode.targetNotExist);
            }
            userToDepartmentDao.delete(user.getUserToDepartments());
            userToDepartmentDao.save(new UserToDepartment(department, user));
            adminLogBuilder.operation(AdminLogOp.user_moveDepartment).operateDescription(user.getName(),
                    department.getName());
        }
        userDao.save(user);
        return new User_UpdateBaseResponse();
    }

}
