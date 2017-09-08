
package cn.bestsec.dcms.platform.impl.user;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_DeleteByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.User_DeleteByIdRequest;
import cn.bestsec.dcms.platform.api.model.User_DeleteByIdResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.AdminDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.RoleScopeDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.dao.UserToDepartmentDao;
import cn.bestsec.dcms.platform.dao.UserToGroupDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyStepDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.UserToDepartment;
import cn.bestsec.dcms.platform.entity.UserToGroup;
import cn.bestsec.dcms.platform.entity.WorkflowPolicyStep;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 删除用户
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月24日下午12:29:09
 */
@Component
public class UserDeleteById implements User_DeleteByIdApi {

    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserToDepartmentDao userToDepartmentDao;
    @Autowired
    private UserToGroupDao userToGroupDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private WorkflowPolicyStepDao workflowPolicyStepDao;
    @Autowired
    private RoleScopeDao roleScopeDao;
    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional
    public User_DeleteByIdResponse execute(User_DeleteByIdRequest user_DeleteByIdRequest) throws ApiException {
        User user = userDao.findByPkUid(user_DeleteByIdRequest.getUid());
        // 判断用户是否不存在或已删除
        if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.userNotExist);
        }

        AdminLogBuilder adminLogBuilder = user_DeleteByIdRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.user_delete)
                .admin(user_DeleteByIdRequest.tokenWrapper().getAdmin()).operateDescription(user.getName());

        // 该用户配置了管理员角色，不能删除
        Admin admin = adminDao.findByAccountAndAdminStateNot(user.getAccount(),
                AdminConsts.AdminState.deleted.getCode());
        if (admin != null) {
            throw new ApiException(ErrorCode.userAlreadyBeAdmin);
        }
        // 如果用户有未办完的审批，不能删除
        List<Role> rolesForFkUid = user.getRolesForFkUid();
        if (rolesForFkUid == null) {
            rolesForFkUid = new ArrayList<>();
        }
        List<Role> rolesForFkAgent = user.getRolesForFkAgentUid();
        if (rolesForFkAgent != null && rolesForFkAgent.size() > 0) {
            for (Role role : rolesForFkAgent) {
                rolesForFkUid.add(role);
            }
        }

        List<WorkflowPolicyStep> rolesForFkUids = workflowPolicyStepDao.findByRoleIn(rolesForFkUid);
        if (rolesForFkUids != null && rolesForFkUids.size() > 0) {
            throw new ApiException(ErrorCode.roleAlreadyInWorkflowPolicy);
        }
        /*
         * List<WorkflowTrack> tracks = workflowTrackDao.findByUser(user); for
         * (WorkflowTrack track : tracks) { if (track.getApproveState() ==
         * WorkflowTrackConsts.state.notApproved.getCode() &&
         * track.getWorkflow().getFlowState() ==
         * WorkFlowConsts.State.notcomplete.getCode()) { throw new
         * ApiException(ErrorCode.userhasUnfinishedApproval); } }
         */

        // 删除用户
        user.setUserState(UserConsts.State.deleted.getCode());
        userDao.save(user);

        // 删除用户部门关联
        List<UserToDepartment> userToDepartments = user.getUserToDepartments();
        if (userToDepartments != null) {
            userToDepartmentDao.delete(userToDepartments);
        }

        // 删除用户组关联
        List<UserToGroup> userToGroups = user.getUserToGroups();
        if (userToGroups != null) {
            userToGroupDao.delete(userToGroups);
        }

        // 当用户作为代理人时，删除代理人记录
        List<Role> agentUser = user.getRolesForFkAgentUid();
        if (agentUser != null && agentUser.size() > 0) {
            for (Role role : agentUser) {
                role.setUserByFkAgentUid(null);
            }
            roleDao.save(agentUser);
        }

        // 删除用户角色关联
        List<Role> roles = user.getRolesForFkUid();
        roleScopeDao.deleteByRoleIn(roles);
        if (roles != null && !roles.isEmpty()) {
            roleDao.delete(roles);
        }

        // 删除Token关联
        tokenDao.delete(user.getTokens());

        return new User_DeleteByIdResponse();
    }

}
