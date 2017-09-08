package cn.bestsec.dcms.platform.impl.workFlow;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.WorkFlow_QueryAgentApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.AgentRoleInfo;
import cn.bestsec.dcms.platform.api.model.UserSimpleInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryAgentRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryAgentResponse;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;

/**
 * 查询委托审核人列表
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年1月3日下午12:10:07
 */
@Component
public class WorkFlowQueryAgent implements WorkFlow_QueryAgentApi {
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional
    public WorkFlow_QueryAgentResponse execute(WorkFlow_QueryAgentRequest workFlow_QueryAgentRequest)
            throws ApiException {
        Token token = tokenDao.findByToken(workFlow_QueryAgentRequest.getToken());
        User user = token.getUser();
        // 查询该用户的所有代理人
        List<Role> roles = roleDao.findUserRoles(user.getPkUid());
        List<AgentRoleInfo> agentRoleList = new ArrayList<AgentRoleInfo>();
        AgentRoleInfo agentRoleInfo = null;
        UserSimpleInfo approveAgent = null;
        for (Role role : roles) {
            agentRoleInfo = new AgentRoleInfo();
            agentRoleInfo.setAgentInvalidTime(role.getFkAgentInvalidTime());
            approveAgent = new UserSimpleInfo();
            // 获取代理人
            User userByFkAgentUid = role.getUserByFkAgentUid();
            approveAgent.setAccount(userByFkAgentUid.getAccount());
            approveAgent.setLevel(userByFkAgentUid.getUserLevel());
            approveAgent.setName(userByFkAgentUid.getName());
            approveAgent.setOnline(UserConsts.userOnline(userByFkAgentUid));
            approveAgent.setUid(userByFkAgentUid.getPkUid());
            agentRoleInfo.setApproveAgent(approveAgent);
            agentRoleInfo.setFileLevel(role.getFileLevel());
            agentRoleInfo.setRoleId(role.getId());
            agentRoleInfo.setRoleType(role.getRoleType());
            agentRoleList.add(agentRoleInfo);
        }
        WorkFlow_QueryAgentResponse resp = new WorkFlow_QueryAgentResponse();
        resp.setAgentRoleList(agentRoleList);
        return resp;
    }

}
