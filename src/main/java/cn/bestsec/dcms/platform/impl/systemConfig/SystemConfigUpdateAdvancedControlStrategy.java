package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateAdvancedControlStrategyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateAdvancedControlStrategyRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateAdvancedControlStrategyResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改高级管控策略配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月26日 下午2:33:44
 */
@Component
public class SystemConfigUpdateAdvancedControlStrategy implements SystemConfig_UpdateAdvancedControlStrategyApi {

    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private SystemConfigDao systemConfigDao;

    @Override
    @Transactional
    public SystemConfig_UpdateAdvancedControlStrategyResponse execute(
            SystemConfig_UpdateAdvancedControlStrategyRequest systemConfig_UpdateAdvancedControlStrategyRequest)
            throws ApiException {

        AdminLogBuilder adminLogBuilder = systemConfig_UpdateAdvancedControlStrategyRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_advancedControlStrategy)
                .admin(systemConfig_UpdateAdvancedControlStrategyRequest.tokenWrapper().getAdmin())
                .operateDescription();

        Integer prohibitDelete = systemConfig_UpdateAdvancedControlStrategyRequest.getProhibitDelete();
        Integer prohibitMailSend = systemConfig_UpdateAdvancedControlStrategyRequest.getProhibitMailSend();
        Integer prohibitNetwork = systemConfig_UpdateAdvancedControlStrategyRequest.getProhibitNetwork();
        Integer prohibitRename = systemConfig_UpdateAdvancedControlStrategyRequest.getProhibitRename();
        Integer prohibitRightSend = systemConfig_UpdateAdvancedControlStrategyRequest.getProhibitRightSend();

        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        JSONObject json = new JSONObject();
        json.put("prohibitDelete", prohibitDelete);
        json.put("prohibitMailSend", prohibitMailSend);
        json.put("prohibitNetwork", prohibitNetwork);
        json.put("prohibitRename", prohibitRename);
        json.put("prohibitDelete", prohibitDelete);
        json.put("prohibitRightSend", prohibitRightSend);

        systemConfig.setAdvancedControlStrategy(JSONObject.toJSONString(json));
        systemConfigDao.save(systemConfig);

        return new SystemConfig_UpdateAdvancedControlStrategyResponse();
    }

}
