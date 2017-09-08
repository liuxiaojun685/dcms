package cn.bestsec.dcms.platform.impl.group;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Group_DeleteApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Group_DeleteRequest;
import cn.bestsec.dcms.platform.api.model.Group_DeleteResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.GroupConsts;
import cn.bestsec.dcms.platform.dao.GroupDao;
import cn.bestsec.dcms.platform.dao.RoleScopeDao;
import cn.bestsec.dcms.platform.dao.UserToGroupDao;
import cn.bestsec.dcms.platform.entity.Group;
import cn.bestsec.dcms.platform.entity.RoleScope;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 删除用户组 只有系统管理员才可以删除用户组。
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月27日 下午3:07:53
 */
@Component
public class GroupDelete implements Group_DeleteApi {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private UserToGroupDao userToGroupDao;
    @Autowired
    private RoleScopeDao roleScopeDao;

    @Override
    @Transactional
    public Group_DeleteResponse execute(Group_DeleteRequest group_DeleteRequest) throws ApiException {
        Group_DeleteResponse resp = new Group_DeleteResponse();
        Group group = groupDao.findByPkGid(group_DeleteRequest.getGid());
        if (group == null || group.getGroupState() == GroupConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        AdminLogBuilder adminLogBuilder = group_DeleteRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.group_delete)
                .admin(group_DeleteRequest.tokenWrapper().getAdmin()).operateDescription(group.getName());
        
        // 如果组内有成员，不能删除组
        if (!group.getUserToGroups().isEmpty()) {
            throw new ApiException(ErrorCode.groupHaveUser);
        }
        group.setGroupState(GroupConsts.State.deleted.getCode());
        // 删除组时要把所在部门关联一并抹除
        group.setDepartment(null);
        groupDao.save(group);
        List<RoleScope> scopes = roleScopeDao.findByFkVarId(group.getPkGid());
        roleScopeDao.delete(scopes);
        return resp;
    }

}
