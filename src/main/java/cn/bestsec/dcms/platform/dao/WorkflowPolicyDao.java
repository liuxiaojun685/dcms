package cn.bestsec.dcms.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.WorkflowPolicy;

/**
 * 
 * @author Hardsun email:bada@bestsec.cn
 * @time 2017年01月12日上午11:19:30
 */
@Repository
public interface WorkflowPolicyDao
        extends JpaRepository<WorkflowPolicy, Integer>, JpaSpecificationExecutor<WorkflowPolicy> {

//    List<WorkflowPolicy> findByWorkFlowTypeAndFileLevelOrderByStepAsc(Integer workFlowType, Integer applyFileLevel);

    WorkflowPolicy findById(Integer workFlowPolicyId);

    WorkflowPolicy findByWorkFlowTypeAndFileLevel(Integer workFlowType, Integer fileLevel);

}
