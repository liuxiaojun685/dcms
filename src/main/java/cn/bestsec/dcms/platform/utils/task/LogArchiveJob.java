package cn.bestsec.dcms.platform.utils.task;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.consts.BackupHistoryConsts;
import cn.bestsec.dcms.platform.consts.TimeConsts;
import cn.bestsec.dcms.platform.service.LogArchiveService;
import cn.bestsec.dcms.platform.web.ApplicationContextHolder;

public class LogArchiveJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(Job.class);

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        long startTime = System.currentTimeMillis();
        logger.info("[定时任务]" + this.getClass().getSimpleName() + " 开始执行");

        try {
            ApplicationContextHolder.getApplicationContext().getBean(LogArchiveService.class)
                    .logArchive(BackupHistoryConsts.CreateFrom.auto.getCode(), new Date());
        } catch (ApiException e) {
            e.printStackTrace();
        }

        long executeTime = System.currentTimeMillis() - startTime;
        logger.info("[定时任务]" + this.getClass().getSimpleName() + " 结束   用时" + TimeConsts.formatTime(executeTime));
    }
}
