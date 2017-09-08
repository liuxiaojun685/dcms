package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.MarkKeyHistory;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日下午12:09:09
 */
@Repository
public interface MarkKeyHistoryDao
        extends JpaRepository<MarkKeyHistory, Integer>, JpaSpecificationExecutor<MarkKeyHistory> {
    @Query("from MarkKeyHistory mh where mh.keyId=:keyId order by mh.createTime desc")
    List<MarkKeyHistory> findNewestByKeyId(@Param("keyId") String keyId);
}
