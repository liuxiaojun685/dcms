package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.ClientLog;

/**
 * 终端日志数据层
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月5日 下午2:24:30
 */
@Repository
public interface ClientLogDao extends JpaRepository<ClientLog, Integer>, JpaSpecificationExecutor<ClientLog> {

    @Query("from ClientLog a where (a.createTime<:timeBefore1 and a.operateType in (select r.operateType from RiskLevelPolicy r where r.riskLevel=1))"
            + " or (a.createTime<:timeBefore2 and a.operateType in (select r.operateType from RiskLevelPolicy r where r.riskLevel=2))"
            + " or (a.createTime<:timeBefore3 and a.operateType in (select r.operateType from RiskLevelPolicy r where r.riskLevel=3))")
    List<ClientLog> findNeedArchive(@Param("timeBefore1") Long timeBefore1, @Param("timeBefore2") Long timeBefore2,
            @Param("timeBefore3") Long timeBefore3);

    @Query("from ClientLog c where c.client.pkCid=:cid and c.user.pkUid=:uid and c.fileByFkSrcFid.pkFid=:fid and c.createTime=:createTime and c.operateType=:operateType")
    List<ClientLog> findBySameAs(@Param("cid") String cid, @Param("uid") String uid, @Param("fid") String fid,
            @Param("createTime") Long createTime, @Param("operateType") String operateType);

    @Query("from ClientLog c where c.fileByFkSrcFid.pkFid=:fid and c.operateType=:operateType and c.srcName=:fileName and c.operateResult=1")
    List<ClientLog> findByFileByFkSrcFidAndOperateTypeAndSrcName(@Param("fid") String fid,
            @Param("operateType") String operateType, @Param("fileName") String fileName);

    @Query("from ClientLog c where c.fileByFkSrcFid.pkFid=:fid and c.desName=:desName")
    List<ClientLog> findByFileByFkSrcFidAndDesName(@Param("fid") String fid, @Param("desName") String desName);
}
