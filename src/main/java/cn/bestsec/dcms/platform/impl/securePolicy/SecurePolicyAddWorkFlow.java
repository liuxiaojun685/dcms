package cn.bestsec.dcms.platform.impl.securePolicy;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_AddWorkFlowApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_AddWorkFlowRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_AddWorkFlowResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.WorkFlowConsts;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyDao;
import cn.bestsec.dcms.platform.entity.WorkflowPolicy;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 新建工作流策略
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日上午11:56:40
 */
@Component
public class SecurePolicyAddWorkFlow implements SecurePolicy_AddWorkFlowApi {

    @Autowired
    private WorkflowPolicyDao workflowPolicyDao;

    @Override
    @Transactional
    public SecurePolicy_AddWorkFlowResponse execute(SecurePolicy_AddWorkFlowRequest securePolicy_AddWorkFlowRequest)
            throws ApiException {
        AdminLogBuilder adminLogBuilder = securePolicy_AddWorkFlowRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_addWorkflowPolicy)
                .admin(securePolicy_AddWorkFlowRequest.tokenWrapper().getAdmin()).operateDescription(
                        FileConsts.Level.parse(securePolicy_AddWorkFlowRequest.getFileLevel()).getDescription(),
                        WorkFlowConsts.Type.parse(securePolicy_AddWorkFlowRequest.getWorkFlowType()).getDescription());
        // 确定该流程类型策略是否已存在
        // 根据流程类型和其所在当前步骤以及文件密级能确定唯一性
        WorkflowPolicy workflowPolicy = workflowPolicyDao.findByWorkFlowTypeAndFileLevel(
                securePolicy_AddWorkFlowRequest.getWorkFlowType(), securePolicy_AddWorkFlowRequest.getFileLevel());
        if (workflowPolicy != null) {
            throw new ApiException(ErrorCode.sameConfitAlreadyExist);
        }
        workflowPolicy = new WorkflowPolicy();
        workflowPolicy.setFileLevel(securePolicy_AddWorkFlowRequest.getFileLevel());
        workflowPolicy.setWorkFlowType(securePolicy_AddWorkFlowRequest.getWorkFlowType());
        workflowPolicy.setCreateTime(System.currentTimeMillis());
        workflowPolicyDao.save(workflowPolicy);
        SecurePolicy_AddWorkFlowResponse resp = new SecurePolicy_AddWorkFlowResponse();
        resp.setWorkFlowPolicyId(workflowPolicy.getId());
        return resp;
    }

}
