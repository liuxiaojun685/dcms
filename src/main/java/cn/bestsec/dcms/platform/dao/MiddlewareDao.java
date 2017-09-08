package cn.bestsec.dcms.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Middleware;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年1月4日下午4:31:02
 */
@Repository
public interface MiddlewareDao extends JpaRepository<Middleware, Integer> {

	Middleware findById(Integer id);
}
