package cn.bestsec.dcms.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.SystemConfig;

/**
 * 系统配置数据层
 * 
 * @author liuqiang
 *
 */

@Repository
public interface SystemConfigDao extends JpaRepository<SystemConfig, Integer> {

}
