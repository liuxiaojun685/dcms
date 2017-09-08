package cn.bestsec.dcms.platform.impl.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Statistics_ClientOnlineApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.LongPoint;
import cn.bestsec.dcms.platform.api.model.Statistics_ClientOnlineRequest;
import cn.bestsec.dcms.platform.api.model.Statistics_ClientOnlineResponse;
import cn.bestsec.dcms.platform.dao.ClientDao;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * 统计终端在线
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年4月19日 下午2:32:37
 */
@Component
public class Statistics_ClientOnline implements Statistics_ClientOnlineApi {

    @Autowired
    private ClientDao clientDao;

    @Override
    @Transactional
    public Statistics_ClientOnlineResponse execute(Statistics_ClientOnlineRequest statistics_ClientOnlineRequest)
            throws ApiException {

        List<String> x = new ArrayList<>();
        x.add("总数");
        x.add("在线");
        x.add("离线");

        List<LongPoint> y = new ArrayList<>();
        Map<String, Long> map = clientDao.statisticsOnline(SystemProperties.getInstance().getUserOfflineTime());
        Long total = map.getOrDefault("totalNum", 0L);
        Long online = map.getOrDefault("onlineNum", 0L);
        Long offline = map.getOrDefault("offlineNum", 0L);
        y.add(new LongPoint(total));
        y.add(new LongPoint(online));
        y.add(new LongPoint(offline));
        Statistics_ClientOnlineResponse resp = new Statistics_ClientOnlineResponse();
        resp.setX(x);
        resp.setY(y);
        return resp;
    }

}
