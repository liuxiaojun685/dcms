
package cn.bestsec.dcms.platform.impl.group;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Group_AddApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Group_AddRequest;
import cn.bestsec.dcms.platform.api.model.Group_AddResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.DepartmentConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.GroupDao;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.Group;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.IdUtils;

/**
 * 创建用户组
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月26日下午4:42:48
 */
@Component
public class GroupAdd implements Group_AddApi {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public Group_AddResponse execute(Group_AddRequest group_AddRequest) throws ApiException {
        AdminLogBuilder adminLogBuilder = group_AddRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.group_create)
                .admin(group_AddRequest.tokenWrapper().getAdmin()).operateDescription(group_AddRequest.getName());

        Department department = departmentDao.findByPkDid(group_AddRequest.getDid());
        if (department == null || department.getDepartmentState() == DepartmentConsts.state.deleted.getCode()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        // 不用判断是否建重复
        Group group = new Group();
        if (group_AddRequest.getName() != null) {
            group.setName(group_AddRequest.getName());
        }
        if (group_AddRequest.getDescription() != null) {
            group.setDescription(group_AddRequest.getDescription());
        }

        // 默认状态都为0
        group.setGroupState(0);
        group.setCreateTime(System.currentTimeMillis());
        group.setPkGid(IdUtils.randomId(IdUtils.prefix_group_id));
        group.setDepartment(department);
        groupDao.save(group);

        Group_AddResponse resp = new Group_AddResponse();
        resp.setGid(group.getPkGid());
        return resp;
    }

}
