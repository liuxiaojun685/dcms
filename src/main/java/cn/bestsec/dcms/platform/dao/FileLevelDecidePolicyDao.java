package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.FileLevelDecidePolicy;

/**
 * 文件定密策略数据层
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月30日 上午10:16:37
 */
@Repository
public interface FileLevelDecidePolicyDao
        extends JpaRepository<FileLevelDecidePolicy, Integer>, JpaSpecificationExecutor<FileLevelDecidePolicy> {

    FileLevelDecidePolicy findByFileStateAndFileLevelAndRoleType(Integer fileState, Integer fileLevel,
            Integer roleType);

    List<FileLevelDecidePolicy> findByFileStateAndFileLevel(Integer fileState, Integer fileLevel);

}
