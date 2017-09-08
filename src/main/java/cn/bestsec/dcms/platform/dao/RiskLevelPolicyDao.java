package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.RiskLevelPolicy;

/**
* @author bada email:bada@bestsec.cn
* @time 2017年2月27日 上午11:46:44
*/
@Repository
public interface RiskLevelPolicyDao extends JpaRepository<RiskLevelPolicy, Integer>, JpaSpecificationExecutor<RiskLevelPolicy> {

    RiskLevelPolicy findByOperateType(String type);
    
    @Query("select r.operateType from RiskLevelPolicy r where r.riskLevel=:level")
    List<String> findByRiskLevel(@Param("level") Integer riskLevel);
}
