package cn.bestsec.dcms.platform.impl.department;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Department_DeleteByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Department_DeleteByIdRequest;
import cn.bestsec.dcms.platform.api.model.Department_DeleteByIdResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.DepartmentConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.RoleScopeDao;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.RoleScope;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 删除部门
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日上午9:57:40
 */
@Component
public class DepartmentDeleteById implements Department_DeleteByIdApi {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private RoleScopeDao roleScopeDao;

    @Override
    @Transactional
    public Department_DeleteByIdResponse execute(Department_DeleteByIdRequest department_DeleteByIdRequest)
            throws ApiException {
        Department department = departmentDao.findByPkDid(department_DeleteByIdRequest.getDid());
        if (department == null || department.getDepartmentState() == DepartmentConsts.state.deleted.getCode()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        AdminLogBuilder adminLogBuilder = department_DeleteByIdRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.department_delete)
                .admin(department_DeleteByIdRequest.tokenWrapper().getAdmin()).operateDescription(department.getName());
        
        // 根部门不能删除
        if (department.getRoot() == DepartmentConsts.state.rootdoor.getCode()) {
            throw new ApiException(ErrorCode.departmentRootCannotBeDeleted);
        }
        // 如果部门或子部门内有用户，不能删除
        if (hasUserOrGroupInDepartment(department)) {
            throw new ApiException(ErrorCode.departmentHaveUserOrGroup);
        }

        department.setDepartmentState(DepartmentConsts.state.deleted.getCode());
        // 删除部门时要把父部门关联一并抹除
        department.setFkParentId("");
        departmentDao.save(department);
        List<RoleScope> scopes = roleScopeDao.findByFkVarId(department.getPkDid());
        roleScopeDao.delete(scopes);
        return new Department_DeleteByIdResponse();
    }

    private boolean hasUserOrGroupInDepartment(Department department) {
        // 判断此部门是否有用户或组
        if (!department.getUserToDepartments().isEmpty() || !department.getGroups().isEmpty()) {
            return true;
        }
        // 查找子部门
        List<Department> childDepartments = departmentDao.findByFkParentId(department.getPkDid());
        for (Department childDepartment : childDepartments) {
            // 判断子部门是否有用户或组
            if (hasUserOrGroupInDepartment(childDepartment)) {
                return true;
            }
        }
        return false;
    }

}
