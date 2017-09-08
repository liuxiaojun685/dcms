package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateTimerConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateTimerConfigRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateTimerConfigResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.service.TimerScheduleService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改定时配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月3日 下午4:55:11
 */
@Component
public class SystemConfigUpdateTimerConfig implements SystemConfig_UpdateTimerConfigApi {

    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private TimerScheduleService timerScheduleService;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_UpdateTimerConfigResponse execute(
            SystemConfig_UpdateTimerConfigRequest systemConfig_UpdateTimerConfigRequest) throws ApiException {
        SystemConfig_UpdateTimerConfigResponse resp = new SystemConfig_UpdateTimerConfigResponse();
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        // 文件自动解密定时配置周期 毫秒
        Long timerAutoDecodePeriod = systemConfig_UpdateTimerConfigRequest.getTimerAutoDecodePeriod();
        if (timerAutoDecodePeriod != null) {
            systemConfig.setTimerAutoDecodePeriod(timerAutoDecodePeriod);
        }
        // 文件自动解密定时配置起始时间
        Long timerAutoDecodeStartTime = systemConfig_UpdateTimerConfigRequest.getTimerAutoDecodeStartTime();
        if (timerAutoDecodeStartTime != null) {
            systemConfig.setTimerAutoDecodeStartTime(timerAutoDecodeStartTime);
        }
        // 数据库备份定时配置周期 毫秒
        Long timerBackupPeriod = systemConfig_UpdateTimerConfigRequest.getTimerBackupPeriod();
        if (timerBackupPeriod != null) {
            systemConfig.setTimerBackupPerriod(timerBackupPeriod);
        }
        // 数据库备份定时配置起始时间
        Long timerBackupStartTime = systemConfig_UpdateTimerConfigRequest.getTimerBackupStartTime();
        if (timerBackupStartTime != null) {
            systemConfig.setTimerBackupStartTime(timerBackupStartTime);
        }
        // 取消审批代理人权限定时配置周期 毫秒
        Long timerCancelApproverPeriod = systemConfig_UpdateTimerConfigRequest.getTimerCancelApproverPeriod();
        if (timerCancelApproverPeriod != null) {
            systemConfig.setTimerCancelApproverPeriod(timerCancelApproverPeriod);
        }
        // 取消审批代理人权限定时配置起始时间
        Long timerCancelApproverStartTime = systemConfig_UpdateTimerConfigRequest.getTimerCancelApproverStartTime();
        if (timerCancelApproverStartTime != null) {
            systemConfig.setTimerCancelApproverStartTime(timerCancelApproverStartTime);
        }
        // 日志上传定时配置周期 毫秒
        Long timerClientLogPeriod = systemConfig_UpdateTimerConfigRequest.getTimerClientLogPeriod();
        if (timerClientLogPeriod != null) {
            systemConfig.setTimerClientLogPeriod(timerClientLogPeriod);
        }
        // 日志上传定时配置起始时间
        Long timerClientLogStartTime = systemConfig_UpdateTimerConfigRequest.getTimerClientLogStartTime();
        if (timerClientLogStartTime != null) {
            systemConfig.setTimerClientLogStartTime(timerClientLogStartTime);
        }
        // 目录同步定时配置周期 毫秒
        Long timerDirSyncPeriod = systemConfig_UpdateTimerConfigRequest.getTimerAdSyncPeriod();
        if (timerDirSyncPeriod != null) {
            systemConfig.setTimerDirSyncPeriod(timerDirSyncPeriod);
        }
        // 目录同步定时配置起始时间
        Long timerDirSyncStartTime = systemConfig_UpdateTimerConfigRequest.getTimerAdSyncStartTime();
        if (timerDirSyncStartTime != null) {
            systemConfig.setTimerDirSyncStartTime(timerDirSyncStartTime);
        }
        // 密级文件统计定时配置周期 毫秒
        Long timerFileStatisticPeriod = systemConfig_UpdateTimerConfigRequest.getTimerFileStatisticPeriod();
        if (timerFileStatisticPeriod != null) {
            systemConfig.setTimerFileStatisticPeriod(timerFileStatisticPeriod);
        }
        // 密级文件统计定时配置起始时间
        Long timerFileStatisticStartTime = systemConfig_UpdateTimerConfigRequest.getTimerFileStatisticStartTime();
        if (timerFileStatisticStartTime != null) {
            systemConfig.setTimerFileStatisticStartTime(timerFileStatisticStartTime);
        }
        // 邮件告警定时配置周期 毫秒
        Long timerMailAlarmPeriod = systemConfig_UpdateTimerConfigRequest.getTimerMailAlarmPeriod();
        if (timerMailAlarmPeriod != null) {
            systemConfig.setTimerMailAlarmPeriod(timerMailAlarmPeriod);
        }
        // 邮件告警定时配置起始时间
        Long timerMailAlarmStartTime = systemConfig_UpdateTimerConfigRequest.getTimerMailAlarmStartTime();
        if (timerMailAlarmStartTime != null) {
            systemConfig.setTimerMailAlarmStartTime(timerMailAlarmStartTime);
        }
        systemConfigDao.save(systemConfig);

        AdminLogBuilder adminLogBuilder = systemConfig_UpdateTimerConfigRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_timer)
                .admin(systemConfig_UpdateTimerConfigRequest.tokenWrapper().getAdmin()).operateDescription();

        timerScheduleService.scheduleTimer();
        return resp;
    }

}
