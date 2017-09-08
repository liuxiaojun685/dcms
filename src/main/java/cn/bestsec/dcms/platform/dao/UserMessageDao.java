package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.UserMessage;

@Repository
public interface UserMessageDao extends JpaRepository<UserMessage, Integer>, JpaSpecificationExecutor<UserMessage> {
    @Query("from UserMessage um where um.fkUid=:uid")
    List<UserMessage> findUerMessage(@Param("uid") String uid);
}
