package cn.bestsec.dcms.platform.impl.workFlow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.WorkFlow_CanCreateApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.WorkFlow_CanCreateRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_CanCreateResponse;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.WorkFlowConsts;
import cn.bestsec.dcms.platform.dao.WorkFlowDao;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.Workflow;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年4月12日 下午4:05:07
 */
@Component
public class WorkFlowCanCreate implements WorkFlow_CanCreateApi {
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private WorkFlowDao workFlowDao;

    @Override
    @Transactional
    public WorkFlow_CanCreateResponse execute(WorkFlow_CanCreateRequest request) throws ApiException {
        User user = request.tokenWrapper().getUser();

        try {
            if (!securityPolicyService.canCreateWorkflow(request.getSrcFid(), user.getPkUid(),
                    request.getWorkFlowType(), request.getFileState(), request.getApplyFileLevel())) {
                String level = FileConsts.Level.parse(request.getApplyFileLevel()).getDescription();
                String state = FileConsts.State.parse(request.getFileState()).getDescription();
                String type = WorkFlowConsts.Type.parse(request.getWorkFlowType()).getDescription();
                return new WorkFlow_CanCreateResponse(ErrorCode.operationNotPermitted.getCode(),
                        "您无权对该" + state + "文件 进行" + type + "申请");
            }

            // 同一文件，不能申请相同流程，如果流程审批被退回，则可以再次申请
            // 1.申请后流程未完成 2.流程通过完成后不能申请（除了审批不通过）
            List<Workflow> workflows = workFlowDao.findSame(request.getWorkFlowType(), request.getSrcFid());
            if (!workflows.isEmpty()) {
                throw new ApiException(ErrorCode.workflowAlreadyExists);
            }
        } catch (ApiException e) {
            ErrorCode ec = e.getErrorCode();
            if (ec.getCode() == ErrorCode.permissionDenied.getCode()) {
                throw new ApiException(ErrorCode.fileNoPermission);
            }
            throw e;
        }
        return new WorkFlow_CanCreateResponse();
    }

}
