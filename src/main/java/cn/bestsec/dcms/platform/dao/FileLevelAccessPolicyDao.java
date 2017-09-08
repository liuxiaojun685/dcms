package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.FileLevelAccessPolicy;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日下午2:42:19
 */
@Repository
public interface FileLevelAccessPolicyDao extends JpaRepository<FileLevelAccessPolicy, Integer> {
    FileLevelAccessPolicy findByFileLevelAndUserLevel(Integer fileLevel, Integer userLevel);

    List<FileLevelAccessPolicy> findByFileLevelAndEnable(Integer fileLevel, Integer enable);
    
    @Query("select p.fileLevel from FileLevelAccessPolicy p where p.userLevel=:userLevel and p.enable=1")
    List<Integer> findSupportFileLevel(@Param("userLevel") int userLevel);
}
