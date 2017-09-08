package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.Workflow;
import cn.bestsec.dcms.platform.entity.WorkflowTrack;

/**
 * @author hardsun email:bada@bestsec.cn
 * @time 2017年1月12日 上午11:58:46
 */
public interface WorkflowTrackDao
        extends JpaRepository<WorkflowTrack, Integer>, JpaSpecificationExecutor<WorkflowTrack> {
    List<WorkflowTrack> findByUser(User user);

    @Query("from WorkflowTrack t where t.approveStep=:step and t.workflow.id=:workflowId")
    List<WorkflowTrack> findByApproveStepAndWorkflowId(@Param("step") Integer step, @Param("workflowId") Integer workflowId);

    @Query("from WorkflowTrack t where t.workflow.id=:workflowId")
    List<WorkflowTrack> fileWorkflowId(@Param("workflowId") Integer workflowId);

    List<WorkflowTrack> findByWorkflowOrderByApproveStepAsc(Workflow workFlow);

    @Query("from WorkflowTrack t where t.user.pkUid=:pkUid and t.approveState=0 and t.workflow.currentStep=t.approveStep")
    List<WorkflowTrack> findMyTurn(@Param("pkUid") String pkUid);
}
