package cn.bestsec.dcms.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.BackupHistory;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日下午2:46:19
 */
@Repository
public interface BackupHistoryDao extends JpaRepository<BackupHistory, Integer> {
    BackupHistory findById(Integer id);

    BackupHistory findByOrderByCreateTimeDesc();
}
