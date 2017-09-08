package cn.bestsec.dcms.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日上午11:28:21
 */
@Repository
public interface FileLevelValidPeriodDao extends JpaRepository<FileLevelValidPeriod, Integer> {
	FileLevelValidPeriod findByfilelevel(int fileLevel);

}
