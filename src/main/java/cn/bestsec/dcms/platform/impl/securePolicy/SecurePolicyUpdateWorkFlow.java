package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_UpdateWorkFlowApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdateWorkFlowRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdateWorkFlowResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.WorkFlowConsts;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyStepDao;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.WorkflowPolicy;
import cn.bestsec.dcms.platform.entity.WorkflowPolicyStep;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改工作流策略 安全管理员
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月30日 下午2:52:46
 */
@Component
public class SecurePolicyUpdateWorkFlow implements SecurePolicy_UpdateWorkFlowApi {
    @Autowired
    private WorkflowPolicyDao workflowPolicyDao;
    @Autowired
    private WorkflowPolicyStepDao workflowPolicyStepDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional
    public SecurePolicy_UpdateWorkFlowResponse execute(
            SecurePolicy_UpdateWorkFlowRequest securePolicy_UpdateWorkFlowRequest) throws ApiException {
        WorkflowPolicy workflowPolicy = workflowPolicyDao
                .findById(securePolicy_UpdateWorkFlowRequest.getWorkFlowPolicyId());
        if (workflowPolicy == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        List<WorkflowPolicyStep> steps = workflowPolicyStepDao.findByFkWorkFlowPolicyIdAndStep(workflowPolicy.getId(),
                securePolicy_UpdateWorkFlowRequest.getStep());
        List<WorkflowPolicyStep> newSteps = new ArrayList<>();
        for (Integer roleId : securePolicy_UpdateWorkFlowRequest.getRoleId()) {
            Role role = roleDao.findById(roleId);
            if (role == null) {
                continue;
            }
            // 如果角色不支持该密级的流程，跳过
            if ((role.getFileLevel() & (1 << workflowPolicy.getFileLevel())) == 0) {
                continue;
            }
            newSteps.add(
                    new WorkflowPolicyStep(role, securePolicy_UpdateWorkFlowRequest.getStep(), workflowPolicy.getId()));
        }
        workflowPolicyStepDao.delete(steps);
        workflowPolicyStepDao.save(newSteps);

        AdminLogBuilder adminLogBuilder = securePolicy_UpdateWorkFlowRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_updateWorkflowPolicy)
                .admin(securePolicy_UpdateWorkFlowRequest.tokenWrapper().getAdmin())
                .operateDescription(FileConsts.Level.parse(workflowPolicy.getFileLevel()).getDescription(),
                        WorkFlowConsts.Type.parse(workflowPolicy.getWorkFlowType()).getDescription(),
                        securePolicy_UpdateWorkFlowRequest.getStep(), newSteps.size());

        return new SecurePolicy_UpdateWorkFlowResponse();
    }

}
