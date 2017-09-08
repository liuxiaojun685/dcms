package cn.bestsec.dcms.platform.impl.department;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Department_DeleteUserApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Department_DeleteUserRequest;
import cn.bestsec.dcms.platform.api.model.Department_DeleteUserResponse;
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
 * 部门移除用户 只有系统管理员可以移除
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月28日 下午1:44:01
 */
@Component
public class DepartmentDeleteUser implements Department_DeleteUserApi {

    @Autowired
    private UserToDepartmentDao userToDepartmentDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public Department_DeleteUserResponse execute(Department_DeleteUserRequest department_DeleteUserRequest)
            throws ApiException {
        Department department = departmentDao.findByPkDid(department_DeleteUserRequest.getDid());
        if (department == null || department.getDepartmentState() == DepartmentConsts.state.deleted.getCode()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        User user = userDao.findByPkUid(department_DeleteUserRequest.getUid());
        if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.userNotExist);
        }
        
        List<UserToDepartment> userToDepartment = user.getUserToDepartments();
        userToDepartmentDao.delete(userToDepartment);

        AdminLogBuilder adminLogBuilder = department_DeleteUserRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.department_addUser)
                .admin(department_DeleteUserRequest.tokenWrapper().getAdmin())
                .operateDescription(user.getName(), department.getName());

        return new Department_DeleteUserResponse();
    }

}
