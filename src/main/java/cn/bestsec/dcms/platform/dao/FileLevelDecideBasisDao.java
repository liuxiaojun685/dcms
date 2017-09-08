package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.api.model.BasisClassItem;
import cn.bestsec.dcms.platform.api.model.BasisItem;
import cn.bestsec.dcms.platform.api.model.BasisItemItem;
import cn.bestsec.dcms.platform.entity.FileLevelDecideBasis;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日下午3:45:54
 */
@Repository
public interface FileLevelDecideBasisDao
        extends JpaRepository<FileLevelDecideBasis, Integer>, JpaSpecificationExecutor<FileLevelDecideBasis> {
    FileLevelDecideBasis findById(Integer id);

    List<FileLevelDecideBasis> findByBasisType(Integer basisType);

    List<FileLevelDecideBasis> findByBasisName(String basisName);

    List<FileLevelDecideBasis> findByBasisClass(String basisClass);
    
    @Query("from FileLevelDecideBasis b where b.basisLevel>=0 and b.basisLevel<=:basisLevel and b.basisType=:basisType")
    List<FileLevelDecideBasis> findByBasisLevelAndBasisType(@Param("basisLevel") Integer basisLevel, @Param("basisType") Integer basisType);

    @Query("select new cn.bestsec.dcms.platform.api.model.BasisClassItem(min(b.id), b.basisClass) from FileLevelDecideBasis b where b.basisType=:basisType group by b.basisClass")
    List<BasisClassItem> findBasisClassList(@Param("basisType") Integer basisType);

    @Query("select new cn.bestsec.dcms.platform.api.model.BasisItem(b.id, b.basisName) from FileLevelDecideBasis b where b.id in (select min(b.id) from FileLevelDecideBasis b where b.basisType=:basisType and b.basisClass=:basisClass group by b.basisName)")
    List<BasisItem> findBasisList(@Param("basisType") Integer basisType, @Param("basisClass") String basisClass);

    @Query("select new cn.bestsec.dcms.platform.api.model.BasisItemItem(b.id, b.basisLevel, b.basisItem) from FileLevelDecideBasis b where b.basisType=:basisType and b.basisClass=:basisClass and b.basisName=:basisName")
    List<BasisItemItem> findBasisItemList(@Param("basisType") Integer basisType, @Param("basisClass") String basisClass,
            @Param("basisName") String basisName);
}
