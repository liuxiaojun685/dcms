package cn.bestsec.dcms.platform.impl.department;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Department_QueryByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Department_QueryByIdRequest;
import cn.bestsec.dcms.platform.api.model.Department_QueryByIdResponse;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.entity.Department;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月17日 下午2:36:26
 */
@Component
public class DepartmentQueryById implements Department_QueryByIdApi {
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public Department_QueryByIdResponse execute(Department_QueryByIdRequest department_QueryByIdRequest)
            throws ApiException {
        Department department = departmentDao.findByPkDid(department_QueryByIdRequest.getDid());
        if (department == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        Department_QueryByIdResponse resp = new Department_QueryByIdResponse();
        resp.setDescription(department.getDescription());
        resp.setDid(department.getPkDid());
        resp.setName(department.getName());
        if (department.getFkParentId() != null) {
            Department parent = departmentDao.findByPkDid(department.getFkParentId());
            if (parent != null) {
                resp.setParentName(parent.getName());
            }
        }
        return resp;
    }

}
