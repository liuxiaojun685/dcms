package cn.bestsec.dcms.platform.impl.role;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Role_DeleteByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Role_DeleteByIdRequest;
import cn.bestsec.dcms.platform.api.model.Role_DeleteByIdResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.RoleConsts;
import cn.bestsec.dcms.platform.consts.WorkFlowConsts;
import cn.bestsec.dcms.platform.consts.WorkflowTrackConsts;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.RoleScopeDao;
import cn.bestsec.dcms.platform.dao.WorkflowTrackDao;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.WorkflowTrack;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 删除用户角色
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日上午10:58:16
 */
@Component
public class RoleDeleteById implements Role_DeleteByIdApi {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private WorkflowTrackDao workflowTrackDao;
    @Autowired
    private RoleScopeDao roleScopeDao;

    @Override
    @Transactional
    public Role_DeleteByIdResponse execute(Role_DeleteByIdRequest role_DeleteByIdRequest) throws ApiException {
        Role_DeleteByIdResponse resp = new Role_DeleteByIdResponse();
        Role role = roleDao.findOne(role_DeleteByIdRequest.getRoleId());
        if (role == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        AdminLogBuilder adminLogBuilder = role_DeleteByIdRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.role_delete)
                .admin(role_DeleteByIdRequest.tokenWrapper().getAdmin()).operateDescription(
                        role.getUserByFkUid().getName(), RoleConsts.Type.parse(role.getRoleType()).getDescription());

        // 如果流程策略中配置该用户，不能删除
        if (!role.getWorkflowPolicySteps().isEmpty()) {
            throw new ApiException(ErrorCode.roleAlreadyInWorkflowPolicy);
        }

        // 如果用户或其代理有未办完的审批，不能删除
        List<WorkflowTrack> tracks = workflowTrackDao.findByUser(role.getUserByFkUid());
        if (role.getUserByFkAgentUid() != null) {
            tracks.addAll(workflowTrackDao.findByUser(role.getUserByFkAgentUid()));
        }
        for (WorkflowTrack track : tracks) {
            if (track.getApproveState() == WorkflowTrackConsts.state.notApproved.getCode()
                    && track.getWorkflow().getFlowState() == WorkFlowConsts.State.notcomplete.getCode()) {
                throw new ApiException(ErrorCode.userhasUnfinishedApproval);
            }
        }

//        List<Role> roles = roleDao.findByTypeAndLevel(role.getRoleType(), role.getFileLevel());
//        if (roles.size() == 1) {
//            role_DeleteByIdRequest.clearAdminLogBuilder();
//            throw new ApiException(ErrorCode.roleIsLastOneInType);
//        }
        
        roleScopeDao.delete(role.getRoleScopes());
        roleDao.delete(role);

        return resp;
    }

}
