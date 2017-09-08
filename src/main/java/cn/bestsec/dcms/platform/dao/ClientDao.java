package cn.bestsec.dcms.platform.dao;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Client;

/**
 * 终端数据层
 * 
 * @author liuqiang
 *
 */
@Repository
public interface ClientDao extends JpaRepository<Client, Integer>, JpaSpecificationExecutor<Client> {

    Client findByPkCid(String id);

    Client findByMacAndClientStateNot(String mac, Integer clientState);

    @Query("select count(case when c.heartbeatTime+:offlineTime>unix_timestamp(now())*1000 then 0 end) as onlineNum, count(case when c.heartbeatTime+:offlineTime<=unix_timestamp(now())*1000 then 0 end) as offlineNum, count(*) as totalNum from Client c where c.clientState!=1")
    Map<String, Long> statisticsOnline(@Param("offlineTime") long offlineTime);

}
