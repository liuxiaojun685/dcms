package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.UserMessageHistory;

@Repository
public interface UserMessageHistoryDao extends JpaRepository<UserMessageHistory, Integer>, JpaSpecificationExecutor<UserMessageHistory> {
    @Query("from UserMessageHistory u where u.fkUid=:uid")
    List<UserMessageHistory> findUerMessageHistory(@Param("uid") String uid);
}
