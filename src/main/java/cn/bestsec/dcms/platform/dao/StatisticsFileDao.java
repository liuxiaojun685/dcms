package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.dao.entity.StatisticsFileTrendEntity;
import cn.bestsec.dcms.platform.entity.StatisticsFile;

/**
 * 统计文件
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月26日 下午5:46:14
 */
@Repository
public interface StatisticsFileDao extends JpaRepository<StatisticsFile, Integer> {
    StatisticsFile findByCreateTime(Long createTime);

    /**
     * 按天分组统计操作总数
     * @param startTime
     * @param endTime
     * @return month %Y.%m, classifiedNum, issuedNum, classifiedChangeNum,
     *         declassifiedNum, outsourceNum,
     */
    @Query("select new cn.bestsec.dcms.platform.dao.entity.StatisticsFileTrendEntity(min(date_format(from_unixtime(sf.createTime/1000), '%Y.%m.%d')), sum(sf.classifiedNum), sum(sf.issuedNum), sum(sf.classifiedChangeNum), sum(sf.declassifiedNum), sum(sf.outsourceNum))"
            + " from StatisticsFile sf where sf.createTime>=:startTime and sf.createTime<:endTime"
            + " group by from_unixtime(sf.createTime/1000, '%Y.%m.%d') order by any_value(sf.createTime)")
    List<StatisticsFileTrendEntity> statisticsByDay(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
    
    /**
     * 按月分组统计操作总数
     * @param startTime
     * @param endTime
     * @return month %Y.%m, classifiedNum, issuedNum, classifiedChangeNum,
     *         declassifiedNum, outsourceNum,
     */
    @Query("select new cn.bestsec.dcms.platform.dao.entity.StatisticsFileTrendEntity(min(date_format(from_unixtime(sf.createTime/1000), '%Y.%m')), sum(sf.classifiedNum), sum(sf.issuedNum), sum(sf.classifiedChangeNum), sum(sf.declassifiedNum), sum(sf.outsourceNum))"
            + " from StatisticsFile sf where sf.createTime>=:startTime and sf.createTime<:endTime"
            + " group by from_unixtime(sf.createTime/1000, '%Y.%m') order by any_value(sf.createTime)")
    List<StatisticsFileTrendEntity> statisticsByMonth(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    /**
     * 按年分组统计操作总数
     * @param startTime
     * @param endTime
     * @return month %Y, classifiedNum, issuedNum, classifiedChangeNum,
     *         declassifiedNum, outsourceNum,
     */
    @Query("select new cn.bestsec.dcms.platform.dao.entity.StatisticsFileTrendEntity(min(date_format(from_unixtime(sf.createTime/1000), '%Y')), sum(sf.classifiedNum), sum(sf.issuedNum), sum(sf.classifiedChangeNum), sum(sf.declassifiedNum), sum(sf.outsourceNum))"
            + " from StatisticsFile sf where sf.createTime>=:startTime and sf.createTime<:endTime"
            + " group by from_unixtime(sf.createTime/1000, '%Y') order by any_value(sf.createTime)")
    List<StatisticsFileTrendEntity> statisticsByYear(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
