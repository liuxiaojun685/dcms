package cn.bestsec.dcms.platform.impl.statistics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.Statistics_QueryWorkFlowListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.LongPoint;
import cn.bestsec.dcms.platform.api.model.Statistics_QueryWorkFlowListRequest;
import cn.bestsec.dcms.platform.api.model.Statistics_QueryWorkFlowListResponse;
import cn.bestsec.dcms.platform.dao.StatisticsSpecDao;
import cn.bestsec.dcms.platform.dao.WorkFlowDao;
import cn.bestsec.dcms.platform.dao.entity.StatisticsWorkflowEntity;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年4月19日下午5:07:59
 */
@Component
public class QueryWorkFlowList implements Statistics_QueryWorkFlowListApi {
    @Autowired
    private WorkFlowDao workFlowDao;
    @Autowired
    private StatisticsSpecDao statisticsSpecDao;

    @Override
    @Transactional
    public Statistics_QueryWorkFlowListResponse execute(Statistics_QueryWorkFlowListRequest request)
            throws ApiException {
        List<String> x = new ArrayList<>();
        x.add("已完成");
        x.add("未完成");
        List<LongPoint> y = new ArrayList<>();
        StatisticsWorkflowEntity entity = statisticsSpecDao.getWorkflowCount(request.getWorkflowType(),
                request.getStartTime(), request.getEndTime());
        y.add(new LongPoint(entity.getFinish()));
        y.add(new LongPoint(entity.getUnfinish()));

        return new Statistics_QueryWorkFlowListResponse(x, y);
    }

}
