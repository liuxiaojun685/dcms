package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryAdvancedControlStrategyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryAdvancedControlStrategyRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryAdvancedControlStrategyResponse;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;

/**
 * 获取高级管控策略配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月26日 下午2:14:04
 */
@Component
public class SystemConfigQueryAdvancedControlStrategy implements SystemConfig_QueryAdvancedControlStrategyApi {

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_QueryAdvancedControlStrategyResponse execute(
            SystemConfig_QueryAdvancedControlStrategyRequest systemConfig_QueryAdvancedControlStrategyRequest)
            throws ApiException {
        SystemConfig_QueryAdvancedControlStrategyResponse resp = new SystemConfig_QueryAdvancedControlStrategyResponse();
        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        resp.setProhibitDelete(CommonConsts.Bool.no.getInt());
        resp.setProhibitMailSend(CommonConsts.Bool.no.getInt());
        resp.setProhibitNetwork(CommonConsts.Bool.no.getInt());
        resp.setProhibitRename(CommonConsts.Bool.no.getInt());
        resp.setProhibitRightSend(CommonConsts.Bool.no.getInt());
        String advancedControlStrategy = systemConfig.getAdvancedControlStrategy();
        JSONObject json = JSONObject.parseObject(advancedControlStrategy);

        if (json != null && !json.isEmpty()) {
            resp.setProhibitDelete(json.getInteger("prohibitDelete"));
            resp.setProhibitMailSend(json.getInteger("prohibitMailSend"));
            resp.setProhibitNetwork(json.getInteger("prohibitNetwork"));
            resp.setProhibitRename(json.getInteger("prohibitRename"));
            resp.setProhibitRightSend(json.getInteger("prohibitRightSend"));
        }
        return resp;
    }

}
