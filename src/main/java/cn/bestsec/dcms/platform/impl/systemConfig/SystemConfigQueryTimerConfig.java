package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryTimerConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryTimerConfigRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryTimerConfigResponse;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;

/**
 * 获取定时配置
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日上午10:47:01
 */
@Component
public class SystemConfigQueryTimerConfig implements SystemConfig_QueryTimerConfigApi {
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_QueryTimerConfigResponse execute(
            SystemConfig_QueryTimerConfigRequest systemConfig_QueryTimerConfigRequest) throws ApiException {
        SystemConfig_QueryTimerConfigResponse resp = new SystemConfig_QueryTimerConfigResponse();
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        resp.setTimerAutoDecodePeriod(systemConfig.getTimerAutoDecodePeriod());
        resp.setTimerAutoDecodeStartTime(systemConfig.getTimerAutoDecodeStartTime());
        resp.setTimerBackupPeriod(systemConfig.getTimerBackupPerriod());
        resp.setTimerBackupStartTime(systemConfig.getTimerBackupStartTime());
        resp.setTimerCancelApproverPeriod(systemConfig.getTimerCancelApproverPeriod());
        resp.setTimerCancelApproverStartTime(systemConfig.getTimerCancelApproverStartTime());
        resp.setTimerClientLogPeriod(systemConfig.getTimerClientLogPeriod());
        resp.setTimerClientLogStartTime(systemConfig.getTimerClientLogStartTime());
        resp.setTimerAdSyncPeriod(systemConfig.getTimerDirSyncPeriod());
        resp.setTimerAdSyncStartTime(systemConfig.getTimerDirSyncStartTime());
        resp.setTimerFileStatisticPeriod(systemConfig.getTimerFileStatisticPeriod());
        resp.setTimerFileStatisticStartTime(systemConfig.getTimerFileStatisticStartTime());
        resp.setTimerMailAlarmPeriod(systemConfig.getTimerMailAlarmPeriod());
        resp.setTimerMailAlarmStartTime(systemConfig.getTimerMailAlarmStartTime());
        return resp;
    }

}
