package cn.bestsec.dcms.platform.impl.role;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Role_DeleteByIdForceApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Role_DeleteByIdForceRequest;
import cn.bestsec.dcms.platform.api.model.Role_DeleteByIdForceResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.FileConsts;
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
 * 6.5 删除用户角色（强制）
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年2月8日 下午3:52:56
 */
@Component
public class RoleDeleteByIdForce implements Role_DeleteByIdForceApi {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private WorkflowTrackDao workflowTrackDao;
    @Autowired
    private RoleScopeDao roleScopeDao;

    @Override
    @Transactional
    public Role_DeleteByIdForceResponse execute(Role_DeleteByIdForceRequest role_DeleteByIdForceRequest)
            throws ApiException {
        Role role = roleDao.findById(role_DeleteByIdForceRequest.getRoleId());
        if (role == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        AdminLogBuilder adminLogBuilder = role_DeleteByIdForceRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.role_delete)
                .admin(role_DeleteByIdForceRequest.tokenWrapper().getAdmin()).operateDescription(role.getUserByFkUid().getName(),
                        FileConsts.Level.parse(role.getFileLevel()).getDescription(),
                        RoleConsts.Type.parse(role.getRoleType()).getDescription());

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

        roleScopeDao.delete(role.getRoleScopes());
        roleDao.delete(role);
        return new Role_DeleteByIdForceResponse();
    }

}
