package cn.bestsec.dcms.platform.impl.workFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.WorkFlow_QueryApproverSelectListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.RoleSimpleInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlowRoleInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryApproverSelectListRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryApproverSelectListResponse;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.RoleScopeDao;
import cn.bestsec.dcms.platform.dao.WorkFlowDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyStepDao;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.Workflow;
import cn.bestsec.dcms.platform.entity.WorkflowPolicy;
import cn.bestsec.dcms.platform.entity.WorkflowPolicyStep;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月3日 下午3:58:14
 */
@Component
public class WorkFlowQueryApproverSelectList implements WorkFlow_QueryApproverSelectListApi {
    @Autowired
    private WorkflowPolicyDao workflowPolicyDao;
    @Autowired
    private WorkflowPolicyStepDao workflowPolicyStepDao;
    @Autowired
    private WorkFlowDao workflowDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private RoleScopeDao roleScopeDao;

    @Override
    @Transactional
    public WorkFlow_QueryApproverSelectListResponse execute(
            WorkFlow_QueryApproverSelectListRequest workFlow_QueryApproverSelectListRequest) throws ApiException {
        WorkflowPolicy workflowPolicy = null;
        User nowUser = workFlow_QueryApproverSelectListRequest.tokenWrapper().getUser();
        // 流程发起人
        User createUser = null;
        if (workFlow_QueryApproverSelectListRequest.getWorkFlowId() != null) {
            Workflow workflow = workflowDao.findById(workFlow_QueryApproverSelectListRequest.getWorkFlowId());
            if (workflow != null) {
                workflowPolicy = workflowPolicyDao.findByWorkFlowTypeAndFileLevel(workflow.getWorkFlowType(),
                        workflow.getApplyFileLevel());
                createUser = workflow.getUser();
            }
        } else {
            workflowPolicy = workflowPolicyDao.findByWorkFlowTypeAndFileLevel(
                    workFlow_QueryApproverSelectListRequest.getWorkFlowType(),
                    workFlow_QueryApproverSelectListRequest.getApplyFileLevel());

        }
        if (workflowPolicy == null) {
            throw new ApiException(ErrorCode.workflowPolicyError);
        }

        WorkFlow_QueryApproverSelectListResponse resp = new WorkFlow_QueryApproverSelectListResponse();
        List<WorkFlowRoleInfo> respStepList = new ArrayList<>();
        if (workFlow_QueryApproverSelectListRequest.getStep() != null) {
            WorkFlowRoleInfo info = buildWorkFlowRoleInfo(workflowPolicy.getId(),
                    workFlow_QueryApproverSelectListRequest.getStep(), nowUser, createUser);
            if (info == null) {
                info = new WorkFlowRoleInfo(new ArrayList<>(), workFlow_QueryApproverSelectListRequest.getStep());
            }
            respStepList.add(info);
        } else {
            for (int step = 1;; step++) {
                WorkFlowRoleInfo info = buildWorkFlowRoleInfo(workflowPolicy.getId(), step, nowUser, createUser);
                if (info == null) {
                    break;
                }
                respStepList.add(info);
            }
        }
        resp.setStepList(respStepList);
        resp.setTotalStep(respStepList.size());
        return resp;
    }

    WorkFlowRoleInfo buildWorkFlowRoleInfo(int workflowPolicyId, int step, User userNow, User createUser) {
        List<WorkflowPolicyStep> stepList = workflowPolicyStepDao.findByFkWorkFlowPolicyIdAndStep(workflowPolicyId,
                step);
        if (stepList.isEmpty()) {
            return null;
        }
        // 用户所在的部门、组列表
        String uid = userNow.getPkUid();
        if (createUser != null) {
            uid = createUser.getPkUid();
        }
        Set<String> privateVarIds = securityPolicyService.getPrivateVarIds(uid);
        WorkFlowRoleInfo respWorkFlowRoleInfo = new WorkFlowRoleInfo();
        List<RoleSimpleInfo> respRoleList = new ArrayList<>();
        for (WorkflowPolicyStep stepInfo : stepList) {
            Role role = stepInfo.getRole();
            User user = role.getUserByFkUid();
            if (userNow.getPkUid().equals(user.getPkUid())) {
                continue;
            }
            // 审批人是否可管理所在用户部门
            // 审批人可视范围的部门组
            List<String> scopes = roleScopeDao.findByRoleId(role.getId());
            // 获取管辖的所有子部门
            List<String> scopeAll = new ArrayList<>();
            // 查找所有子部门
            for (String did : scopes) {
                securityPolicyService.childDepartment(did, scopeAll);
            }

            // key 有一个相同，则可以返回，申请用户所在组 部门 可视范围都不在审批人之内，则不返回
            Boolean key = false;
            for (String scopeId : scopeAll) {
                if (privateVarIds.contains(scopeId)) {
                    key = true;
                    break;
                }

            }
            if (!key) {
                continue;
            }
            RoleSimpleInfo respRoleInfo = new RoleSimpleInfo();
            respRoleInfo.setAccount(user.getAccount());
            respRoleInfo.setLevel(user.getUserLevel());
            respRoleInfo.setName(user.getName());
            respRoleInfo.setOnline(UserConsts.userOnline(user));
            respRoleInfo.setRoleId(role.getId());
            respRoleInfo.setRoleType(role.getRoleType());
            respRoleInfo.setUid(user.getPkUid());
            respRoleList.add(respRoleInfo);
        }
        respWorkFlowRoleInfo.setRoleList(respRoleList);
        respWorkFlowRoleInfo.setStep(step);
        return respWorkFlowRoleInfo;
    }
}
