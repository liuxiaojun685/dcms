package cn.bestsec.dcms.platform.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.consts.TimeConsts;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.service.TimerScheduleService;
import cn.bestsec.dcms.platform.utils.task.AdSyncJob;
import cn.bestsec.dcms.platform.utils.task.BackupJob;
import cn.bestsec.dcms.platform.utils.task.CancelAgentJob;
import cn.bestsec.dcms.platform.utils.task.CleanJob;
import cn.bestsec.dcms.platform.utils.task.FileDecryptJob;
import cn.bestsec.dcms.platform.utils.task.FileStatisticJob;
import cn.bestsec.dcms.platform.utils.task.JobManager;
import cn.bestsec.dcms.platform.utils.task.JobManager.JobType;
import cn.bestsec.dcms.platform.utils.task.LogArchiveJob;
import cn.bestsec.dcms.platform.utils.task.MailAlarmJob;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月20日 下午1:37:32
 */
@Service
public class TimerScheduleServiceImpl implements TimerScheduleService {
    static Logger logger = LoggerFactory.getLogger(TimerScheduleServiceImpl.class);
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public boolean scheduleTimer() {
        SystemConfig systemConfig = null;
        try {
            systemConfig = systemConfigService.getSystemConfig();
        } catch (ApiException e) {
        }
        if (systemConfig == null) {
            logger.warn("系统配置不正确，无法启动定时任务");
            return false;
        }

        excuteOne(AdSyncJob.class, systemConfig.getTimerDirSyncStartTime(), systemConfig.getTimerDirSyncPeriod(), true);
        excuteOne(FileDecryptJob.class, systemConfig.getTimerAutoDecodeStartTime(),
                systemConfig.getTimerAutoDecodePeriod(), true);
        excuteOne(CancelAgentJob.class, systemConfig.getTimerCancelApproverStartTime(),
                systemConfig.getTimerCancelApproverPeriod(), true);
        excuteOne(BackupJob.class, systemConfig.getTimerBackupStartTime(), systemConfig.getTimerBackupPerriod(),
                CommonConsts.Bool.parse(systemConfig.getAutoBackupEnable()).getBoolean());
        excuteOne(FileStatisticJob.class, systemConfig.getTimerFileStatisticStartTime(),
                systemConfig.getTimerFileStatisticPeriod(), true);
        excuteOne(MailAlarmJob.class, systemConfig.getTimerMailAlarmStartTime(), systemConfig.getTimerMailAlarmPeriod(),
                true);
        excuteOne(MailAlarmJob.class, systemConfig.getTimerMailAlarmStartTime(), systemConfig.getTimerMailAlarmPeriod(),
                true);
        excuteOne(LogArchiveJob.class, systemConfig.getTimerClientLogStartTime(),
                systemConfig.getTimerClientLogPeriod(),
                CommonConsts.Bool.parse(systemConfig.getAutoLogArchiveEnable()).getBoolean());
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 56);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        excuteOne(CleanJob.class, cal.getTimeInMillis(), systemConfig.getTimerMailAlarmPeriod(), true);

        return true;
    }

    private void excuteOne(Class<? extends Job> cls, Long startTimeMillis, Long repeatMillis, boolean enable) {
        JobManager manager = JobManager.getInstance();
        if (enable) {
            if (manager.checkExist(cls)) {
                JobType job = manager.getJobType(cls);
                if (TimeConsts.getClockTime(job.startTimeMillis) != TimeConsts.getClockTime(startTimeMillis)
                        || TimeConsts.getTime(job.repeatMillis) != TimeConsts.getTime(repeatMillis)) {
                    manager.modifyJobTime(job, TimeConsts.getNearbyDateTime(startTimeMillis), repeatMillis);
                }
            } else {
                manager.addJob(cls, TimeConsts.getNearbyDateTime(startTimeMillis), repeatMillis);
            }
        } else {
            if (manager.checkExist(cls)) {
                JobType job = manager.getJobType(cls);
                manager.removeJob(job);
            }
        }
    }

}
