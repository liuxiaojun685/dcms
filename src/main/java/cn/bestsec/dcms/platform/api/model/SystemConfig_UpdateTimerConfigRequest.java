package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_UpdateTimerConfigRequest extends CommonRequestFieldsSupport {
    private String token;
    private Long timerMailAlarmStartTime;
    private Long timerMailAlarmPeriod;
    private Long timerBackupStartTime;
    private Long timerBackupPeriod;
    private Long timerClientLogStartTime;
    private Long timerClientLogPeriod;
    private Long timerAdSyncStartTime;
    private Long timerAdSyncPeriod;
    private Long timerAutoDecodeStartTime;
    private Long timerAutoDecodePeriod;
    private Long timerFileStatisticStartTime;
    private Long timerFileStatisticPeriod;
    private Long timerCancelApproverStartTime;
    private Long timerCancelApproverPeriod;
    
    public SystemConfig_UpdateTimerConfigRequest() {
    }

    public SystemConfig_UpdateTimerConfigRequest(String token, Long timerMailAlarmStartTime, Long timerMailAlarmPeriod, Long timerBackupStartTime, Long timerBackupPeriod, Long timerClientLogStartTime, Long timerClientLogPeriod, Long timerAdSyncStartTime, Long timerAdSyncPeriod, Long timerAutoDecodeStartTime, Long timerAutoDecodePeriod, Long timerFileStatisticStartTime, Long timerFileStatisticPeriod, Long timerCancelApproverStartTime, Long timerCancelApproverPeriod) {
        this.token = token;
        this.timerMailAlarmStartTime = timerMailAlarmStartTime;
        this.timerMailAlarmPeriod = timerMailAlarmPeriod;
        this.timerBackupStartTime = timerBackupStartTime;
        this.timerBackupPeriod = timerBackupPeriod;
        this.timerClientLogStartTime = timerClientLogStartTime;
        this.timerClientLogPeriod = timerClientLogPeriod;
        this.timerAdSyncStartTime = timerAdSyncStartTime;
        this.timerAdSyncPeriod = timerAdSyncPeriod;
        this.timerAutoDecodeStartTime = timerAutoDecodeStartTime;
        this.timerAutoDecodePeriod = timerAutoDecodePeriod;
        this.timerFileStatisticStartTime = timerFileStatisticStartTime;
        this.timerFileStatisticPeriod = timerFileStatisticPeriod;
        this.timerCancelApproverStartTime = timerCancelApproverStartTime;
        this.timerCancelApproverPeriod = timerCancelApproverPeriod;
    }

    /**
     * 
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 邮件告警定时配置起始时间
     */
    public Long getTimerMailAlarmStartTime() {
        return timerMailAlarmStartTime;
    }

    public void setTimerMailAlarmStartTime(Long timerMailAlarmStartTime) {
        this.timerMailAlarmStartTime = timerMailAlarmStartTime;
    }

    /**
     * 邮件告警定时配置周期 毫秒
     */
    public Long getTimerMailAlarmPeriod() {
        return timerMailAlarmPeriod;
    }

    public void setTimerMailAlarmPeriod(Long timerMailAlarmPeriod) {
        this.timerMailAlarmPeriod = timerMailAlarmPeriod;
    }

    /**
     * 数据库备份定时配置起始时间
     */
    public Long getTimerBackupStartTime() {
        return timerBackupStartTime;
    }

    public void setTimerBackupStartTime(Long timerBackupStartTime) {
        this.timerBackupStartTime = timerBackupStartTime;
    }

    /**
     * 数据库备份定时配置周期 毫秒
     */
    public Long getTimerBackupPeriod() {
        return timerBackupPeriod;
    }

    public void setTimerBackupPeriod(Long timerBackupPeriod) {
        this.timerBackupPeriod = timerBackupPeriod;
    }

    /**
     * 日志归档定时配置起始时间
     */
    public Long getTimerClientLogStartTime() {
        return timerClientLogStartTime;
    }

    public void setTimerClientLogStartTime(Long timerClientLogStartTime) {
        this.timerClientLogStartTime = timerClientLogStartTime;
    }

    /**
     * 日志归档定时配置周期 毫秒
     */
    public Long getTimerClientLogPeriod() {
        return timerClientLogPeriod;
    }

    public void setTimerClientLogPeriod(Long timerClientLogPeriod) {
        this.timerClientLogPeriod = timerClientLogPeriod;
    }

    /**
     * AD同步定时配置起始时间
     */
    public Long getTimerAdSyncStartTime() {
        return timerAdSyncStartTime;
    }

    public void setTimerAdSyncStartTime(Long timerAdSyncStartTime) {
        this.timerAdSyncStartTime = timerAdSyncStartTime;
    }

    /**
     * AD同步定时配置周期 毫秒
     */
    public Long getTimerAdSyncPeriod() {
        return timerAdSyncPeriod;
    }

    public void setTimerAdSyncPeriod(Long timerAdSyncPeriod) {
        this.timerAdSyncPeriod = timerAdSyncPeriod;
    }

    /**
     * 文件自动解密定时配置起始时间
     */
    public Long getTimerAutoDecodeStartTime() {
        return timerAutoDecodeStartTime;
    }

    public void setTimerAutoDecodeStartTime(Long timerAutoDecodeStartTime) {
        this.timerAutoDecodeStartTime = timerAutoDecodeStartTime;
    }

    /**
     * 文件自动解密定时配置周期 毫秒
     */
    public Long getTimerAutoDecodePeriod() {
        return timerAutoDecodePeriod;
    }

    public void setTimerAutoDecodePeriod(Long timerAutoDecodePeriod) {
        this.timerAutoDecodePeriod = timerAutoDecodePeriod;
    }

    /**
     * 密级文件统计定时配置起始时间
     */
    public Long getTimerFileStatisticStartTime() {
        return timerFileStatisticStartTime;
    }

    public void setTimerFileStatisticStartTime(Long timerFileStatisticStartTime) {
        this.timerFileStatisticStartTime = timerFileStatisticStartTime;
    }

    /**
     * 密级文件统计定时配置周期 毫秒
     */
    public Long getTimerFileStatisticPeriod() {
        return timerFileStatisticPeriod;
    }

    public void setTimerFileStatisticPeriod(Long timerFileStatisticPeriod) {
        this.timerFileStatisticPeriod = timerFileStatisticPeriod;
    }

    /**
     * 取消审批代理人权限定时配置起始时间
     */
    public Long getTimerCancelApproverStartTime() {
        return timerCancelApproverStartTime;
    }

    public void setTimerCancelApproverStartTime(Long timerCancelApproverStartTime) {
        this.timerCancelApproverStartTime = timerCancelApproverStartTime;
    }

    /**
     * 取消审批代理人权限定时配置周期 毫秒
     */
    public Long getTimerCancelApproverPeriod() {
        return timerCancelApproverPeriod;
    }

    public void setTimerCancelApproverPeriod(Long timerCancelApproverPeriod) {
        this.timerCancelApproverPeriod = timerCancelApproverPeriod;
    }
}
