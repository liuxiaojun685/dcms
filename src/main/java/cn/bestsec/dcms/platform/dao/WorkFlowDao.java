package cn.bestsec.dcms.platform.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Workflow;

/**
 * 工作流
 * 
 * @author 刘强 email:liuqiang@bestsec.cno
 * @time 2017年1月13日 下午5:04:14
 */
@Repository
public interface WorkFlowDao extends JpaRepository<Workflow, Integer>, JpaSpecificationExecutor<Workflow> {

    Workflow findById(Integer id);

    /**
     * 统计流程状态
     * 
     * @return "finish" 已完成，"unfinish" 未完成
     */
    @Query("SELECT COUNT(case when w.flowState=1 then 0 end) as finish,COUNT(case when w.flowState=0 then 0 end) as unfinish FROM Workflow w")
    Map<String, Long> statisticsQueryWorkFlowList();

    @Query("from Workflow wf where wf.workFlowType=:workFlowType and wf.fkSrcFid=:srcFid and ((wf.flowState=0) or (wf.flowState=1 and wf.flowResult=1))")
    List<Workflow> findSame(@Param("workFlowType") Integer workFlowType, @Param("srcFid") String srcFid);

}
