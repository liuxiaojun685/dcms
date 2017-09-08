package cn.bestsec.dcms.platform.impl.department;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Department_UpdateByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Department_UpdateByIdRequest;
import cn.bestsec.dcms.platform.api.model.Department_UpdateByIdResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.DepartmentConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 通过部门id修改部门，只有管理员才能修改
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月28日 上午10:59:00
 */
@Component
public class DepartmentUpdateById implements Department_UpdateByIdApi {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public Department_UpdateByIdResponse execute(Department_UpdateByIdRequest department_UpdateByIdRequest)
            throws ApiException {
        // 获取需要修改的部门
        Department department = departmentDao.findByPkDid(department_UpdateByIdRequest.getDid());
        if (department == null || department.getDepartmentState() == DepartmentConsts.state.deleted.getCode()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        AdminLogBuilder adminLogBuilder = department_UpdateByIdRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.department_update)
                .admin(department_UpdateByIdRequest.tokenWrapper().getAdmin()).operateDescription(department.getName());

        if (department_UpdateByIdRequest.getDescription() != null) {
            department.setDescription(department_UpdateByIdRequest.getDescription());
        }
        if (department_UpdateByIdRequest.getName() != null) {
            department.setName(department_UpdateByIdRequest.getName());
        }
        if (department_UpdateByIdRequest.getParentDid() != null) {
            Department parentDepartment;
            if (department_UpdateByIdRequest.getParentDid() == null
                    || department_UpdateByIdRequest.getParentDid().isEmpty()) {
                // 查找根部门
                parentDepartment = departmentDao.findByRoot(1).get(0);
                if (parentDepartment == null) {
                    throw new ApiException(ErrorCode.departmentNotExistsRoot);
                }
            } else {
                parentDepartment = departmentDao.findByPkDid(department_UpdateByIdRequest.getParentDid());
                if (parentDepartment == null
                        || department.getDepartmentState() == DepartmentConsts.state.deleted.getCode()) {
                    throw new ApiException(ErrorCode.targetNotExist);
                }
            }
            department.setFkParentId(parentDepartment.getPkDid());
        }
        departmentDao.save(department);

        Department_UpdateByIdResponse resp = new Department_UpdateByIdResponse();
        resp.setDid(department.getPkDid());
        return resp;
    }

}
