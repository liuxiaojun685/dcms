package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.FileLevelChangeHistory;

/**
 * 文件历史
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月6日 下午4:50:53
 */
@Repository
public interface FileLevelChangeHistoryDao extends JpaRepository<FileLevelChangeHistory, Integer> {

    @Query("from FileLevelChangeHistory ch where ch.userByFkUid.pkUid=:pkUid")
    List<FileLevelChangeHistory> findByUserByFkFileLevelDecideUid(@Param("pkUid") String pkUid);

    @Query("from FileLevelChangeHistory ch where ch.file.pkFid=:fid")
    List<FileLevelChangeHistory> findByFid(@Param("fid") String fid);

    List<FileLevelChangeHistory> findByfileOrderByFileLevelChangeTimeAsc(File file);

    @Query("from FileLevelChangeHistory ch where ch.file.pkFid=:fid and ch.fileLevelChangeTime=:time and ch.levelAltered=:la and ch.durationAltered=:da")
    List<FileLevelChangeHistory> findSame(@Param("fid") String fid, @Param("time") Long time,
            @Param("la") Integer levelAltered, @Param("da") Integer durationAltered);

    @Query("from FileLevelChangeHistory ch where ch.file.pkFid=:lastFid and ch.levelAltered=:la and ch.durationAltered=:da")
    List<FileLevelChangeHistory> findByFidAndLevelAlteredAndDurationAltered(@Param("lastFid") String lastFid,
            @Param("la") Integer levelAltered, @Param("da") Integer durationAltered);

}
