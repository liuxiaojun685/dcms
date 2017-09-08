package cn.bestsec.dcms.platform.impl.statistics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.Statistics_AdminUsageApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.LongPoint;
import cn.bestsec.dcms.platform.api.model.Statistics_AdminUsageRequest;
import cn.bestsec.dcms.platform.api.model.Statistics_AdminUsageResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.consts.TimeConsts;
import cn.bestsec.dcms.platform.dao.AdminLogDao;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年4月17日 下午5:00:24
 */
@Component
public class AdminUsage implements Statistics_AdminUsageApi {
    @Autowired
    private AdminLogDao adminLogDao;

    @Override
    @Transactional
    public Statistics_AdminUsageResponse execute(Statistics_AdminUsageRequest statistics_AdminUsageRequest)
            throws ApiException {
        List<String> x = new ArrayList<>();
        Integer adminType = statistics_AdminUsageRequest.getAdminType();
        if (adminType == AdminConsts.Type.sysadmin.getCode()) {
            x.add("登录");
            x.add("注销");
            x.add("修改密码");
            x.add("重置用户密码");
            x.add("用户与组织管理");
            x.add("终端管理");
            x.add("配置管理");
            x.add("授权许可");
            x.add("组件管理");
            x.add("备份管理");
            x.add("集成管理");
        } else if (adminType == AdminConsts.Type.secadmin.getCode()) {
            x.add("登录");
            x.add("注销");
            x.add("修改密码");
            x.add("用户与组织管理");
            x.add("用户角色管理");
            x.add("定密策略管理");
        } else {
            throw new ApiException(ErrorCode.invalidArgument);
        }

        Integer threshold = statistics_AdminUsageRequest.getThreshold();
        Long startTime = 0L;
        if (threshold == 1) {
            startTime = System.currentTimeMillis() - TimeConsts.year_in_millis;
        } else if (threshold == 2) {
            startTime = System.currentTimeMillis() - TimeConsts.month_in_millis;
        } else if (threshold == 3) {
            startTime = System.currentTimeMillis() - TimeConsts.week_in_millis;
        } else if (threshold == 4) {
            startTime = System.currentTimeMillis() - TimeConsts.day_in_millis;
        }

        List<LongPoint> y = new ArrayList<>();
        for (String op : x) {
            y.add(new LongPoint(adminLogDao.statisticsOpCount(op, adminType, startTime)));
        }

        return new Statistics_AdminUsageResponse(x, y);
    }

}
