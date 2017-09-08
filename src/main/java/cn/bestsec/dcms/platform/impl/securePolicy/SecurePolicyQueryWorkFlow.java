package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_QueryWorkFlowApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.RoleSimpleInfo;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryWorkFlowRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryWorkFlowResponse;
import cn.bestsec.dcms.platform.api.model.WorkFlowPolicyInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlowRoleInfo;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyStepDao;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.WorkflowPolicy;
import cn.bestsec.dcms.platform.entity.WorkflowPolicyStep;

/**
 * 9.10 查询工作流策略 安全管理员
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月30日 下午2:56:30
 */
@Component
public class SecurePolicyQueryWorkFlow implements SecurePolicy_QueryWorkFlowApi {
    @Autowired
    private WorkflowPolicyDao workflowPolicyDao;
    @Autowired
    private WorkflowPolicyStepDao workflowPolicyStepDao;

    @Override
    @Transactional
    public SecurePolicy_QueryWorkFlowResponse execute(
            SecurePolicy_QueryWorkFlowRequest securePolicy_QueryWorkFlowRequest) throws ApiException {

        Specification<WorkflowPolicy> spec = buildSpecification(securePolicy_QueryWorkFlowRequest);

        ArrayList<String> sort = new ArrayList<>();
        sort.add("workFlowType");
        sort.add("fileLevel");
        List<WorkflowPolicy> workflowPolicys = workflowPolicyDao.findAll(spec, new Sort(Sort.Direction.ASC, sort));

        SecurePolicy_QueryWorkFlowResponse resp = new SecurePolicy_QueryWorkFlowResponse();
        List<WorkFlowPolicyInfo> workFlowPolicyListDto = new ArrayList<>();
        for (WorkflowPolicy workflowPolicy : workflowPolicys) {
            WorkFlowPolicyInfo workFlowPolicyInfoDto = new WorkFlowPolicyInfo();
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
            workFlowPolicyInfoDto.setWorkFlowRoleList(workFlowRoleListDto);
            workFlowPolicyInfoDto.setFileLevel(workflowPolicy.getFileLevel());
            workFlowPolicyInfoDto.setWorkFlowPolicyId(workflowPolicy.getId());
            workFlowPolicyInfoDto.setWorkFlowType(workflowPolicy.getWorkFlowType());
            workFlowPolicyInfoDto.setCreateTime(workflowPolicy.getCreateTime());
            workFlowPolicyListDto.add(workFlowPolicyInfoDto);
        }
        resp.setWorkFlowPolicyList(workFlowPolicyListDto);
        return resp;
    }

    // 传入参数
    private Specification<WorkflowPolicy> buildSpecification(
            SecurePolicy_QueryWorkFlowRequest securePolicy_QueryWorkFlowRequest) {

        return new Specification<WorkflowPolicy>() {

            @Override
            public Predicate toPredicate(Root<WorkflowPolicy> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 文件密级
                Integer fileLevel = securePolicy_QueryWorkFlowRequest.getFileLevel();
                // 流程类型
                Integer workFlowType = securePolicy_QueryWorkFlowRequest.getWorkFlowType();
                List<Predicate> list = new ArrayList<Predicate>();
                if (fileLevel != null) {
                    list.add(cb.equal(root.get("fileLevel").as(int.class), fileLevel));
                }
                if (workFlowType != null) {
                    list.add(cb.equal(root.get("workFlowType").as(int.class), workFlowType));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
    }

}
