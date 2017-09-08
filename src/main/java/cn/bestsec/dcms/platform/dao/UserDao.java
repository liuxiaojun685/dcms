package cn.bestsec.dcms.platform.dao;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByPkUidAndPasswd(String uid, String passwd);

    User findByAccount(String account);

    User findByAccountAndPasswd(String account, String passwd);

    User findByPkUid(String id);

    /**
     * 统计在线人数
     * 
     * @return "onlineNum" 在线人数，"offlineNum" 离线人数, "totalNum" 总人数
     */
    @Query("select count(case when u.heartbeatTime+:offlineTime>unix_timestamp(now())*1000 then 0 end) as onlineNum, count(case when u.heartbeatTime+:offlineTime<=unix_timestamp(now())*1000 then 0 end) as offlineNum, count(*) as totalNum from User u where u.userState!=1")
    Map<String, Long> statisticsOnline(@Param("offlineTime") long offlineTime);

    @Query("from User u where u.phone=:mid and u.createFrom=4")
    User findMWUserByMid(@Param("mid") String mid);
    
    @Query("from User u where u.lastLoginIp=:ip and u.createFrom=4")
    User findMWUserByIp(@Param("ip") String ip);
}
