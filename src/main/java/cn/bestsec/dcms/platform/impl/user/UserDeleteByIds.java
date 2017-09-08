package cn.bestsec.dcms.platform.impl.user;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_DeleteByIdsApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.User_DeleteByIdsRequest;
import cn.bestsec.dcms.platform.api.model.User_DeleteByIdsResponse;
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
 * 批量删除用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年7月28日 下午3:38:04
 */
@Component
public class UserDeleteByIds implements User_DeleteByIdsApi {

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
    public User_DeleteByIdsResponse execute(User_DeleteByIdsRequest user_DeleteByIdsRequest) throws ApiException {
        List<String> uids = user_DeleteByIdsRequest.getUids();
        // 记录被删除的用户
        List<User> users = new ArrayList<>();
        for (String uid : uids) {

            User user = userDao.findByPkUid(uid);
            // 判断用户是否不存在或已删除
            if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
                continue;
            }

            // 该用户配置了管理员角色，不能删除
            Admin admin = adminDao.findByAccountAndAdminStateNot(user.getAccount(),
                    AdminConsts.AdminState.deleted.getCode());
            if (admin != null) {
                continue;
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
                continue;
            }

            users.add(user);
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
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < users.size(); i++) {
            if (i == 0) {
                sb.append(users.get(i).getName());
            } else {
                sb.append("|" + users.get(i).getName());
            }

        }
        AdminLogBuilder adminLogBuilder = user_DeleteByIdsRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.user_delete)
                .admin(user_DeleteByIdsRequest.tokenWrapper().getAdmin()).operateDescription(sb);
        return new User_DeleteByIdsResponse();
    }

}
