package cn.bestsec.dcms.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.ClientLevelAccessPolicy;

/**
 * 密级终端访问策略数据层
 * @author liuqiang
 *
 */
@Repository
public interface ClientLevelAccessPolicyDao extends JpaRepository<ClientLevelAccessPolicy, Integer> {

    ClientLevelAccessPolicy findByUserLevelAndClientLevel(Integer level, Integer clientLevel);

}
