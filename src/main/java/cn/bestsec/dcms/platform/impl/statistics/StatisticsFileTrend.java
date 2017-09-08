package cn.bestsec.dcms.platform.impl.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Statistics_FileTrendApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.FileTrendPoint;
import cn.bestsec.dcms.platform.api.model.LongPoint;
import cn.bestsec.dcms.platform.api.model.Statistics_FileTrendRequest;
import cn.bestsec.dcms.platform.api.model.Statistics_FileTrendResponse;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.StatisticsFileDao;
import cn.bestsec.dcms.platform.dao.entity.StatisticsFileTrendEntity;
import cn.bestsec.dcms.platform.utils.DateUtils;

/**
 * 文件月数据
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年4月19日 下午3:55:42
 */
@Component
public class StatisticsFileTrend implements Statistics_FileTrendApi {

    @Autowired
    private FileDao fileDao;
    @Autowired
    private StatisticsFileDao statisticsFileDao;

    @Override
    public Statistics_FileTrendResponse execute(Statistics_FileTrendRequest statistics_FileTrendRequest)
            throws ApiException {
        Integer by = statistics_FileTrendRequest.getBy();
        Long startTime = statistics_FileTrendRequest.getStartTime();
        Long endTime = statistics_FileTrendRequest.getEndTime();
        if (startTime == null) {
            startTime = 0L;
        }
        if (endTime == null) {
            endTime = System.currentTimeMillis();
        } else {
            endTime = DateUtils.getNextDay(new Date(endTime), 1).getTime();
        }
        
        List<LongPoint> classifiedPoints = new ArrayList<>();
        List<LongPoint> issuedPoints = new ArrayList<>();
        List<LongPoint> classifiedChangePoints = new ArrayList<>();
        List<LongPoint> declassifiedPoints = new ArrayList<>();
        List<LongPoint> outsourcePoints = new ArrayList<>();

        List<String> x = new ArrayList<>();
        List<StatisticsFileTrendEntity> list = new ArrayList<>();
        if (by == 1) {
            list = statisticsFileDao.statisticsByYear(startTime, endTime);
        } else if (by == 2) {
            list = statisticsFileDao.statisticsByMonth(startTime, endTime);
        } else if (by == 3) {
            list = statisticsFileDao.statisticsByDay(startTime, endTime);
        }
        for (StatisticsFileTrendEntity item : list) {
            x.add(item.time);
            classifiedPoints.add(new LongPoint(item.classifiedNum));
            issuedPoints.add(new LongPoint(item.issuedNum));
            classifiedChangePoints.add(new LongPoint(item.classifiedChangeNum));
            declassifiedPoints.add(new LongPoint(item.declassifiedNum));
            outsourcePoints.add(new LongPoint(item.outsourceNum));
        }
        
        List<FileTrendPoint> points = new ArrayList<>();
        points.add(new FileTrendPoint(classifiedPoints, "正式定密"));
        points.add(new FileTrendPoint(issuedPoints, "文件签发"));
        points.add(new FileTrendPoint(classifiedChangePoints, "密级变更"));
        points.add(new FileTrendPoint(declassifiedPoints, "文件解密"));
        points.add(new FileTrendPoint(outsourcePoints, "文件外发"));
        return new Statistics_FileTrendResponse(x, points);
    }

}
