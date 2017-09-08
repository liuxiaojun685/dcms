package cn.bestsec.dcms.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Component;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日下午1:59:47
 */
@Repository
public interface ComponentDao extends JpaRepository<Component, Integer> {
	Component findById(Integer id);

}
