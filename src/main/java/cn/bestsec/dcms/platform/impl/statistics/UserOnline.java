package cn.bestsec.dcms.platform.impl.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.Statistics_UserOnlineApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.LongPoint;
import cn.bestsec.dcms.platform.api.model.Statistics_UserOnlineRequest;
import cn.bestsec.dcms.platform.api.model.Statistics_UserOnlineResponse;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年4月18日 下午5:19:51
 */
@Component
public class UserOnline implements Statistics_UserOnlineApi {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public Statistics_UserOnlineResponse execute(Statistics_UserOnlineRequest statistics_UserOnlineRequest)
            throws ApiException {
        List<String> x = new ArrayList<>();
        x.add("总数");
        x.add("在线");
        x.add("离线");
        List<LongPoint> y = new ArrayList<>();
        Map<String, Long> map = userDao.statisticsOnline(SystemProperties.getInstance().getUserOfflineTime());
        Long total = map.getOrDefault("totalNum", 0L);
        Long online = map.getOrDefault("onlineNum", 0L);
        Long offline = map.getOrDefault("offlineNum", 0L);
        y.add(new LongPoint(total));
        y.add(new LongPoint(online));
        y.add(new LongPoint(offline));
        return new Statistics_UserOnlineResponse(x, y);
    }

}
