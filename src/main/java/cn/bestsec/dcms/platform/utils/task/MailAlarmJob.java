package cn.bestsec.dcms.platform.utils.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.consts.TimeConsts;
import cn.bestsec.dcms.platform.service.MailService;
import cn.bestsec.dcms.platform.web.ApplicationContextHolder;

public class MailAlarmJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(Job.class);

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        long startTime = System.currentTimeMillis();
        logger.info("[定时任务]" + this.getClass().getSimpleName() + " 开始执行");

        try {
            ApplicationContextHolder.getApplicationContext().getBean(MailService.class).checkResource();;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        long executeTime = System.currentTimeMillis() - startTime;
        logger.info("[定时任务]" + this.getClass().getSimpleName() + " 结束   用时" + TimeConsts.formatTime(executeTime));
    }
}
