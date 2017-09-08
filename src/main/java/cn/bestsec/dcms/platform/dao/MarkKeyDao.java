package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.MarkKey;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日下午12:09:09
 */
@Repository
public interface MarkKeyDao extends JpaRepository<MarkKey, Integer>, JpaSpecificationExecutor<MarkKey> {
    @Query("from MarkKey m where m.enable=1 order by m.markVersion desc")
    List<MarkKey> findEnableKey();

    @Query("from MarkKey m where m.enable=1 and m.markVersion=:markVersion")
    MarkKey findEnableKeyByVersion(@Param("markVersion") Integer markVersion);
    
    MarkKey findByKeyId(String keyId);
}
