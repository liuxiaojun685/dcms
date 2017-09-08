package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_DeleteWorkFlowApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_DeleteWorkFlowRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_DeleteWorkFlowResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.WorkFlowConsts;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyStepDao;
import cn.bestsec.dcms.platform.entity.WorkflowPolicy;
import cn.bestsec.dcms.platform.entity.WorkflowPolicyStep;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 删除工作流策略
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日上午11:58:37
 */
@Component
public class SecurePolicyDeleteWorkFlow implements SecurePolicy_DeleteWorkFlowApi {
    @Autowired
    private WorkflowPolicyDao workflowPolicyDao;
    @Autowired
    private WorkflowPolicyStepDao workflowPolicyStepDao;

    @Override
    @Transactional
    public SecurePolicy_DeleteWorkFlowResponse execute(
            SecurePolicy_DeleteWorkFlowRequest securePolicy_DeleteWorkFlowRequest) throws ApiException {
        WorkflowPolicy workflowPolicy = workflowPolicyDao.findById(securePolicy_DeleteWorkFlowRequest.getWorkFlowPolicyId());
        if (workflowPolicy == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        List<WorkflowPolicyStep> steps = workflowPolicyStepDao.findByFkWorkFlowPolicyId(workflowPolicy.getId());
        workflowPolicyStepDao.delete(steps);
        workflowPolicyDao.delete(workflowPolicy);

        AdminLogBuilder adminLogBuilder = securePolicy_DeleteWorkFlowRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_deleteWorkflowPolicy)
                .admin(securePolicy_DeleteWorkFlowRequest.tokenWrapper().getAdmin()).operateDescription(
                        FileConsts.Level.parse(workflowPolicy.getFileLevel()).getDescription(),
                        WorkFlowConsts.Type.parse(workflowPolicy.getWorkFlowType())
                                .getDescription());
        return new SecurePolicy_DeleteWorkFlowResponse();
    }

}
