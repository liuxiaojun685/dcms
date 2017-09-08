package cn.bestsec.dcms.platform.impl.department;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Department_AddUserApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Department_AddUserRequest;
import cn.bestsec.dcms.platform.api.model.Department_AddUserResponse;
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
 * 部门添加用户，只有管理员可以添加，一个用户只能隶属一个部门
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月28日 上午11:35:16
 */
@Component
public class DepartmentAddUser implements Department_AddUserApi {

    @Autowired
    private UserToDepartmentDao userToDepartmentDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public Department_AddUserResponse execute(Department_AddUserRequest department_AddUserRequest) throws ApiException {
        // 获取部门,用户
        String did = department_AddUserRequest.getDid();
        Department department = departmentDao.findByPkDid(did);
        if (department == null || department.getDepartmentState() == DepartmentConsts.state.deleted.getCode()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        String uid = department_AddUserRequest.getUid();
        User user = userDao.findByPkUid(uid);
        if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.userNotExist);
        }

        AdminLogBuilder adminLogBuilder = department_AddUserRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.department_addUser)
                .admin(department_AddUserRequest.tokenWrapper().getAdmin())
                .operateDescription(user.getName(), department.getName());

        // 判断用户是否已存在其他部门
        List<UserToDepartment> userToDepartmentSet = user.getUserToDepartments();
        if (userToDepartmentSet != null && userToDepartmentSet.size() > 0) {
            throw new ApiException(ErrorCode.userAlreadyExistInOtherDepartment);
        }
        UserToDepartment userToDepartment = new UserToDepartment();
        userToDepartment.setDepartment(department);
        userToDepartment.setUser(user);
        userToDepartmentDao.save(userToDepartment);
        return new Department_AddUserResponse();
    }

}
