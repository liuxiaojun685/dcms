package cn.bestsec.dcms.platform.impl.workFlow;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.WorkFlow_AddAgentApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.WorkFlow_AddAgentRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_AddAgentResponse;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;

/**
 * 17.4 添加审核代理人 权限:终端用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年2月4日 下午2:49:28
 */
@Component
public class WorkFlowAddAgent implements WorkFlow_AddAgentApi {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;

    @Override
    @Transactional
    public WorkFlow_AddAgentResponse execute(WorkFlow_AddAgentRequest workFlow_AddAgentRequest) throws ApiException {
        Role role = roleDao.findById(workFlow_AddAgentRequest.getRoleId());
        if (role == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        // 审核代理人
        User agent = userDao.findByPkUid(workFlow_AddAgentRequest.getApproveAgentUid());

        if (!securityPolicyService.canAccessFile(agent.getUserLevel(), role.getFileLevel())) {
            throw new ApiException(ErrorCode.agentNoPermission);
        }

        role.setUserByFkAgentUid(agent);
        role.setFkAgentInvalidTime(workFlow_AddAgentRequest.getAgentInvalidTime());
        roleDao.save(role);
        WorkFlow_AddAgentResponse resp = new WorkFlow_AddAgentResponse();
        resp.setAgentId(role.getId());
        return resp;
    }

}
