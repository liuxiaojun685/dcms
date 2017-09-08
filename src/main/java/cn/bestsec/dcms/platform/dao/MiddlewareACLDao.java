package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Middleware;
import cn.bestsec.dcms.platform.entity.MiddlewareAcl;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年1月3日上午10:19:46
 */
@Repository
public interface MiddlewareACLDao extends JpaRepository<MiddlewareAcl, Integer> {
    MiddlewareAcl findById(Integer id);

    List<MiddlewareAcl> findByMiddleware(Middleware middleware);
}
