package cn.bestsec.dcms.platform.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.FileLevelDecideUnit;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日下午2:51:50
 */
@Repository
public interface FileLevelDecideUnitDao
        extends JpaRepository<FileLevelDecideUnit, Integer>, JpaSpecificationExecutor<FileLevelDecideUnit> {
    FileLevelDecideUnit findByUnitNo(String unitNo);

    FileLevelDecideUnit findByMajor(Integer major);
    
    List<FileLevelDecideUnit> findByUnitNoIn(Collection<String> unitNos);

}
