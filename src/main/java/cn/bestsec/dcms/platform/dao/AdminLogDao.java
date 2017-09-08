package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.bestsec.dcms.platform.entity.AdminLog;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年1月9日下午3:03:50
 */

public interface AdminLogDao extends JpaRepository<AdminLog, Integer>, JpaSpecificationExecutor<AdminLog> {
    @Query("select count(*) from AdminLog a where a.operateType=:operateType and admin.adminType=:adminType and a.operateTime>=:startTime")
    long statisticsOpCount(@Param("operateType") String operateType, @Param("adminType") Integer adminType,
            @Param("startTime") Long startTime);

    @Query("from AdminLog a where a.operateType<>'日志归档' and (a.operateTime<:timeBefore1 and a.operateType in (select r.operateType from RiskLevelPolicy r where r.riskLevel=1))"
            + " or (a.operateTime<:timeBefore2 and a.operateType in (select r.operateType from RiskLevelPolicy r where r.riskLevel=2))"
            + " or (a.operateTime<:timeBefore3 and a.operateType in (select r.operateType from RiskLevelPolicy r where r.riskLevel=3))")
    List<AdminLog> findNeedArchive(@Param("timeBefore1") Long timeBefore1, @Param("timeBefore2") Long timeBefore2,
            @Param("timeBefore3") Long timeBefore3);
}
