package cn.bestsec.dcms.platform.impl.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Statistics_FileManyTrendApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.FileTrendPoint;
import cn.bestsec.dcms.platform.api.model.LongPoint;
import cn.bestsec.dcms.platform.api.model.Statistics_FileManyTrendRequest;
import cn.bestsec.dcms.platform.api.model.Statistics_FileManyTrendResponse;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.StatisticsSpecDao;
import cn.bestsec.dcms.platform.dao.entity.StatisticsFileEntity;
import cn.bestsec.dcms.platform.utils.DateUtils;

/**
 * 多条件查询
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月12日 下午5:32:25
 */
@Component
public class StatisticsFileManyTrend implements Statistics_FileManyTrendApi {

    @Autowired
    private FileDao fileDao;
    @Autowired
    private StatisticsSpecDao statisticsFileDaoImpl;

    @Override
    @Transactional
    public Statistics_FileManyTrendResponse execute(Statistics_FileManyTrendRequest statistics_FileManyTrendRequest)
            throws ApiException {
        Integer by = statistics_FileManyTrendRequest.getBy();
        Long startTime = statistics_FileManyTrendRequest.getStartTime();
        Long endTime = statistics_FileManyTrendRequest.getEndTime();
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
        // List<LongPoint> outsourcePoints = new ArrayList<>();

        List<String> x = new ArrayList<>();
        Integer fileLevel = statistics_FileManyTrendRequest.getFileLevel();
        // if统计范围为空,密级为空
        List<String> scopes = statistics_FileManyTrendRequest.getScope();
        // 正式定密
        List<StatisticsFileEntity> makeSecrets = statisticsFileDaoImpl.getFileCount(by, fileLevel, scopes, startTime, endTime);
        for (StatisticsFileEntity statisty : makeSecrets) {
            x.add(statisty.getTime());
            classifiedPoints.add(new LongPoint(statisty.getClassified()));
            issuedPoints.add(new LongPoint(statisty.getIssued()));
            classifiedChangePoints.add(new LongPoint(statisty.getClassifiedChange()));
            declassifiedPoints.add(new LongPoint(statisty.getDeclassified()));
        }

        List<FileTrendPoint> points = new ArrayList<>();
        points.add(new FileTrendPoint(classifiedPoints, "正式定密"));
        points.add(new FileTrendPoint(issuedPoints, "文件签发"));
        points.add(new FileTrendPoint(classifiedChangePoints, "密级变更"));
        points.add(new FileTrendPoint(declassifiedPoints, "文件解密"));
        Statistics_FileManyTrendResponse resp = new Statistics_FileManyTrendResponse();
        resp.setX(x);
        resp.setPoints(points);
        return resp;
    }

}
