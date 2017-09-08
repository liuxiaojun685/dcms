package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.ClientRequestLog;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年1月9日下午4:06:12
 */
@Repository
public interface ClientRequestLogDao
		extends JpaRepository<ClientRequestLog, Integer>, JpaSpecificationExecutor<ClientRequestLog> {

    @Query("from ClientRequestLog a where (a.operateTime<:timeBefore1 and a.operateType in (select r.operateType from RiskLevelPolicy r where r.riskLevel=1))"
            + " or (a.operateTime<:timeBefore2 and a.operateType in (select r.operateType from RiskLevelPolicy r where r.riskLevel=2))"
            + " or (a.operateTime<:timeBefore3 and a.operateType in (select r.operateType from RiskLevelPolicy r where r.riskLevel=3))")
    List<ClientRequestLog> findNeedArchive(@Param("timeBefore1") Long timeBefore1, @Param("timeBefore2") Long timeBefore2,
            @Param("timeBefore3") Long timeBefore3);
}
