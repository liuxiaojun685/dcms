package cn.bestsec.dcms.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Group;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月26日下午4:55:27
 */
@Repository
public interface GroupDao extends JpaRepository<Group, Integer>, JpaSpecificationExecutor<Group> {
	Group findByPkGid(String id);

}
