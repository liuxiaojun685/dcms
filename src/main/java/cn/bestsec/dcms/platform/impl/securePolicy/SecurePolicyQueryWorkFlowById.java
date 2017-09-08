package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.SecurePolicy_QueryWorkFlowByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.RoleSimpleInfo;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryWorkFlowByIdRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryWorkFlowByIdResponse;
import cn.bestsec.dcms.platform.api.model.WorkFlowRoleInfo;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyStepDao;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.WorkflowPolicy;
import cn.bestsec.dcms.platform.entity.WorkflowPolicyStep;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月27日 上午10:43:54
 */
@Component
public class SecurePolicyQueryWorkFlowById implements SecurePolicy_QueryWorkFlowByIdApi {
    @Autowired
    private WorkflowPolicyDao workflowPolicyDao;
    @Autowired
    private WorkflowPolicyStepDao workflowPolicyStepDao;

    @Override
    @Transactional
    public SecurePolicy_QueryWorkFlowByIdResponse execute(
            SecurePolicy_QueryWorkFlowByIdRequest securePolicy_QueryWorkFlowByIdRequest) throws ApiException {

        WorkflowPolicy workflowPolicy = workflowPolicyDao
                .findById(securePolicy_QueryWorkFlowByIdRequest.getWorkFlowPolicyId());
        if (workflowPolicy == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        List<WorkFlowRoleInfo> workFlowRoleListDto = new ArrayList<>();
        for (int step = 1;; step++) {
            List<WorkflowPolicyStep> stepList = workflowPolicyStepDao
                    .findByFkWorkFlowPolicyIdAndStep(workflowPolicy.getId(), step);
            if (stepList.isEmpty()) {
                break;
            }
            WorkFlowRoleInfo workFlowRoleInfoDto = new WorkFlowRoleInfo();
            List<RoleSimpleInfo> roleListDto = new ArrayList<>();
            for (WorkflowPolicyStep stepInfo : stepList) {
                User user = stepInfo.getRole().getUserByFkUid();
                RoleSimpleInfo roleSimpleInfoDto = new RoleSimpleInfo();
                roleSimpleInfoDto.setAccount(user.getAccount());
                roleSimpleInfoDto.setLevel(user.getUserLevel());
                roleSimpleInfoDto.setName(user.getName());
                roleSimpleInfoDto.setOnline(UserConsts.userOnline(user));
                roleSimpleInfoDto.setUid(user.getPkUid());
                roleSimpleInfoDto.setRoleId(stepInfo.getRole().getId());
                roleSimpleInfoDto.setRoleType(stepInfo.getRole().getRoleType());
                roleListDto.add(roleSimpleInfoDto);
            }
            workFlowRoleInfoDto.setRoleList(roleListDto);
            workFlowRoleInfoDto.setStep(step);
            workFlowRoleListDto.add(workFlowRoleInfoDto);
        }

        return new SecurePolicy_QueryWorkFlowByIdResponse(workflowPolicy.getId(), workflowPolicy.getFileLevel(),
                workflowPolicy.getWorkFlowType(), workflowPolicy.getCreateTime(), workFlowRoleListDto);
    }

}
