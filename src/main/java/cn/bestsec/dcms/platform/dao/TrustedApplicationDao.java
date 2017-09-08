package cn.bestsec.dcms.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.TrustedApplication;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日上午11:22:24
 */
@Repository
public interface TrustedApplicationDao extends JpaRepository<TrustedApplication, Integer> {

    TrustedApplication findByProcessName(String processName);

    TrustedApplication findById(Integer trustedAppId);

}
