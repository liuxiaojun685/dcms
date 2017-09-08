package cn.bestsec.dcms.platform.impl.workFlow;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.WorkFlow_UpdateAgentByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.WorkFlow_UpdateAgentByIdRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_UpdateAgentByIdResponse;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.entity.Role;

/**
 * 更新审核代理人期限
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年1月3日下午12:08:07
 */
@Component
public class WorkFlowUpdateAgentById implements WorkFlow_UpdateAgentByIdApi {
    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional
    public WorkFlow_UpdateAgentByIdResponse execute(WorkFlow_UpdateAgentByIdRequest workFlow_UpdateAgentByIdRequest)
            throws ApiException {
        Role role = roleDao.findById(workFlow_UpdateAgentByIdRequest.getRoleId());
        if (role == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        role.setFkAgentInvalidTime(workFlow_UpdateAgentByIdRequest.getAgentInvalidTime());
        roleDao.save(role);
        return new WorkFlow_UpdateAgentByIdResponse();
    }

}
