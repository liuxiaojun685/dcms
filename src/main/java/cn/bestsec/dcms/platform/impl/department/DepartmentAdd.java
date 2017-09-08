package cn.bestsec.dcms.platform.impl.department;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Department_AddApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Department_AddRequest;
import cn.bestsec.dcms.platform.api.model.Department_AddResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.consts.DepartmentConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.IdUtils;

/**
 * 创建部门，只有管理员可以创建
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月28日 上午9:45:55
 */
@Component
public class DepartmentAdd implements Department_AddApi {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public Department_AddResponse execute(Department_AddRequest department_AddRequest) throws ApiException {
        AdminLogBuilder adminLogBuilder = department_AddRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.department_create)
                .admin(department_AddRequest.tokenWrapper().getAdmin())
                .operateDescription(department_AddRequest.getName());

        Department parentDepartment;
        // 如果父部门传入空，说明新建部门所属根部门
        if (department_AddRequest.getParentDid() == null || department_AddRequest.getParentDid().isEmpty()) {
            // 查找根部门
            parentDepartment = departmentDao.findByRoot(1).get(0);
            if (parentDepartment == null) {
                throw new ApiException(ErrorCode.departmentNotExistsRoot);
            }
        } else {
            parentDepartment = departmentDao.findByPkDid(department_AddRequest.getParentDid());
            if (parentDepartment == null
                    || parentDepartment.getDepartmentState() == DepartmentConsts.state.deleted.getCode()) {
                throw new ApiException(ErrorCode.targetNotExist);
            }
        }
        // 判断该父部门下是否已经存在相同部门
        Department sameDepartment = departmentDao.findByFkParentIdAndName(department_AddRequest.getParentDid(),
                department_AddRequest.getName());
        if (sameDepartment != null) {
            throw new ApiException(ErrorCode.departmentAlreadyExists);
        }

        Department department = new Department();
        // Did为UUID
        department.setPkDid(IdUtils.randomId(IdUtils.prefix_department_id));
        department.setDescription(department_AddRequest.getDescription());
        department.setName(department_AddRequest.getName());
        department.setFkParentId(department_AddRequest.getParentDid());
        department.setRoot(CommonConsts.Bool.no.getInt());
        department.setDepartmentState(CommonConsts.Bool.no.getInt());
        departmentDao.save(department);
        Department_AddResponse resp = new Department_AddResponse();
        resp.setDid(department.getPkDid());
        return resp;
    }

}
