package cn.bestsec.dcms.platform.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.File;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日下午3:47:20
 */
@Repository
public interface FileDao extends JpaRepository<File, Integer>, JpaSpecificationExecutor<File> {

    File findByPkFid(String id);

    @Query("select f.pkFid from File f where f.userByFkFileLevelDecideUid.pkUid=:pkUid and f.fileState=:code")
    Collection<String> findByUserByFkFileLevelDecideUidAndFileState(@Param("pkUid") String pkUid,
            @Param("code") Integer code);

    @Query("select f.pkFid from File f where f.userByFkFileDispatchUid.pkUid=:pkUid and f.fileState=:code")
    Collection<String> findByUserByFkFileDispatchUidAndFileState(@Param("pkUid") String pkUid,
            @Param("code") Integer code);

    // @Query("select f.pkFid from File f where f.validPeriod +
    // f.fileLevelDecideTime<=:timeValue and f.fileState in (3,4)")
    // List<String> findWillDecryptFiles(@Param("timeValue") Long timeValue);

    @Query("select f.pkFid from File f where ((f.durationType=2 and f.fileDecryptTime<=:timeValue) or (f.durationType=2 and f.fileDecryptTime<=:timeValue) or (f.durationType=4)) and f.fileState in (3,4)")
    List<String> findWillDecryptFiles(@Param("timeValue") Long timeValue);

    @Query("from File f where f.validPeriod + f.fileLevelDecideTime<=:currentTime and f.fileState in (2,3,4)")
    List<File> findNeedDecryptFiles(@Param("currentTime") long currentTime);

    /**
     * 统计文件台账
     * 
     * @return "fixed" 定密，"Issue" 签发, "decrypt" 解密
     */
    @Query("SELECT COUNT(case when f.fileState=2 then 0 end) as fixed,COUNT(case when f.fileState=3 then 0 end) as Issue,COUNT(case when f.fileState=5 then 0 end) as decrypt FROM File f")
    Map<String, Long> statisticsQueryFileList();

    @Query("select count(case when f.fileState=2 and f.fileLevelDecideTime>=:startTime and f.fileLevelDecideTime<:endTime then 0 end) as classified"
            + ", count(case when f.fileState=3 and f.fileDispatchTime>=:startTime and f.fileDispatchTime<:endTime then 0 end) as issued"
            + ", count(case when f.fileLevelChangeTime>=:startTime and f.fileLevelChangeTime<:endTime then 0 end) as classifiedChange"
            + ", count(case when f.fileState=5 and f.fileDecryptTime>=:startTime and f.fileDecryptTime<:endTime then 0 end) as declassified"
            + " from File f")
    Map<String, Long> statisticsFileState(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    @Query("select f.pkFid FROM File f where f.pkFid NOT IN(SELECT a.fkFid from FileAccessScope a) AND (f.fileDispatchExpect = '' OR f.fileDispatchExpect = NULL)")
    List<String> findByScopes();

    @Query("select f.pkFid FROM File f where f.pkFid NOT IN(SELECT a.fkFid from FileAccessScope a)")
    List<String> findFileNoScopes();

    File findByFkParentFid(String pkFid);

    File findByPkFidAndName(String fid, String name);

    @Query("select f.pkFid from File f where f.userByFkFileCreateUid.pkUid=:pkUid and f.outOfDate=0")
    List<String> findByUserByFkFileCreateUid(@Param("pkUid") String pkUid);

    @Query("select count(case when f.fileLevel=0 then 0 end) as open"
            + ", count(case when f.fileLevel=1 then 0 end) as inside"
            + ", count(case when f.fileLevel=2 then 0 end) as secret"
            + ", count(case when f.fileLevel=3 then 0 end) as confidential"
            + ", count(case when f.fileLevel=4 then 0 end) as topSecret"
            + " from File f where f.pkFid in(:fileIdList) and f.outOfDate=0")
    Map<String, Long> statisticsCountByFileLevel(@Param("fileIdList") Set<String> fileIdList);

    @Query("select count(case when f.fileState=1 then 0 end) as preclassified"
            + ", count(case when f.fileState=2 then 0 end) as classified"
            + ", count(case when f.fileState=3 then 0 end) as issued"
            + ", count(case when f.fileState=5 then 0 end) as declassified"
            + " from File f where f.pkFid in(:fileIdList) and f.outOfDate=0")
    Map<String, Long> statisticsCountByFileState(@Param("fileIdList") Set<String> fileIdList);

}
