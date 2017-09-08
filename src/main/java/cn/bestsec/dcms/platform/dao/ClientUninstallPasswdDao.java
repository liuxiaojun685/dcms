package cn.bestsec.dcms.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.ClientUninstallPasswd;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月26日下午3:46:37
 */
@Repository
public interface ClientUninstallPasswdDao extends JpaRepository<ClientUninstallPasswd, Integer> {

}
