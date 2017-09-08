package cn.bestsec.dcms.platform.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.FileDrm;

/**
 * 文件权限控制数据层
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月30日 下午5:09:53
 */
@Repository
public interface FileDrmDao extends JpaRepository<FileDrm, Integer>, JpaSpecificationExecutor<FileDrm> {

    FileDrm findByFkFidAndFkVarId(String fid, String uid);

    List<FileDrm> findByFkFid(String id);

    @Query("select drm.fkFid from FileDrm drm where drm.fkVarId = :pkDid")
    Collection<String> findByFkVarId(@Param("pkDid") String pkDid);

}
