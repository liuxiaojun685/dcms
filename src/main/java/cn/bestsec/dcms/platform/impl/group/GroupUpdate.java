
package cn.bestsec.dcms.platform.impl.group;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Group_UpdateApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Group_UpdateRequest;
import cn.bestsec.dcms.platform.api.model.Group_UpdateResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.DepartmentConsts;
import cn.bestsec.dcms.platform.consts.GroupConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.GroupDao;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.Group;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改用户组
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月26日下午5:59:32
 */
@Component
public class GroupUpdate implements Group_UpdateApi {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public Group_UpdateResponse execute(Group_UpdateRequest group_UpdateRequest) throws ApiException {
        Group group = groupDao.findByPkGid(group_UpdateRequest.getGid());
        if (group == null || group.getGroupState() == GroupConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        AdminLogBuilder adminLogBuilder = group_UpdateRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.group_update)
                .admin(group_UpdateRequest.tokenWrapper().getAdmin()).operateDescription(group.getName());
        
        if (group_UpdateRequest.getName() != null) {
            group.setName(group_UpdateRequest.getName());
        }
        if (group_UpdateRequest.getDescription() != null) {
            group.setDescription(group_UpdateRequest.getDescription());
        }

        Group_UpdateResponse resp = new Group_UpdateResponse();
        if (group_UpdateRequest.getDid() != null) {
            Department department = departmentDao.findByPkDid(group_UpdateRequest.getDid());
            if (department == null || department.getDepartmentState() == DepartmentConsts.state.deleted.getCode()) {
                throw new ApiException(ErrorCode.targetNotExist);
            }
            group.setDepartment(department);
            resp.setGid(department.getPkDid());
        }
        groupDao.save(group);

        return resp;
    }

}
