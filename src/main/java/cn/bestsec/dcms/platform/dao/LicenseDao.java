package cn.bestsec.dcms.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.License;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日下午12:09:09
 */
@Repository
public interface LicenseDao extends JpaRepository<License, Integer> {

}
