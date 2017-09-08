package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.WorkflowPolicyStep;

/**
 * 
 * @author Hardsun email:bada@bestsec.cn
 * @time 2017年01月12日上午11:19:30
 */
@Repository
public interface WorkflowPolicyStepDao
        extends JpaRepository<WorkflowPolicyStep, Integer>, JpaSpecificationExecutor<WorkflowPolicyStep> {
    List<WorkflowPolicyStep> findByFkWorkFlowPolicyIdAndStep(Integer workFlowPolicyId, Integer step);

    List<WorkflowPolicyStep> findByFkWorkFlowPolicyId(Integer workFlowPolicyId);

    List<WorkflowPolicyStep> findByRoleIn(List<Role> rolesForFkUid);
}
