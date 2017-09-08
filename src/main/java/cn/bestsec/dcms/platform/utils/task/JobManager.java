package cn.bestsec.dcms.platform.utils.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bestsec.dcms.platform.consts.TimeConsts;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月20日 上午10:26:08
 */
public class JobManager {
    static Logger logger = LoggerFactory.getLogger(JobManager.class);

    private static JobManager manager;
    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "DCMS_JOBGROUP_NAME";

    private static List<JobType> mJobs = new ArrayList<JobType>();

    public class JobType {
        public String jobName;
        public Class<? extends Job> cls;
        public Long startTimeMillis;
        public Long repeatMillis;
        public JobKey jobKey;
        public TriggerKey triggerKey;

        public JobType(String jobName, Class<? extends Job> cls, Long startTimeMillis, Long repeatMillis, JobKey jobKey,
                TriggerKey triggerKey) {
            this.jobName = jobName;
            this.cls = cls;
            this.startTimeMillis = startTimeMillis;
            this.repeatMillis = repeatMillis;
            this.jobKey = jobKey;
            this.triggerKey = triggerKey;
        }
    }

    public synchronized static JobManager getInstance() {
        if (manager == null) {
            manager = new JobManager();
        }
        return manager;
    }

    public boolean checkExist(Class<? extends Job> cls) {
        for (JobType job : mJobs) {
            if (job.cls.getName().equals(cls.getName())) {
                return true;
            }
        }
        return false;
    }

    public JobType getJobType(Class<? extends Job> cls) {
        for (JobType job : mJobs) {
            if (job.cls.getName().equals(cls.getName())) {
                return job;
            }
        }
        return null;
    }

    public JobType addJob(Class<? extends Job> cls, Long startTimeMillis, Long repeatMillis) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(cls.getName(), JOB_GROUP_NAME).build();// 任务名，任务组，任务执行类
            // 触发器
            TriggerBuilder<Trigger> builder = TriggerBuilder.newTrigger().forJob(jobDetail);
            if (startTimeMillis != null && startTimeMillis >= 0) {
                builder.startAt(new Date(startTimeMillis));
            }
            if (repeatMillis != null && repeatMillis > 0) {
                builder.withIdentity(cls.getName() + "Trigger", JOB_GROUP_NAME).withSchedule(SimpleScheduleBuilder
                        .simpleSchedule().withIntervalInMilliseconds(repeatMillis).repeatForever());
            }
            Trigger trigger = builder.build();
            logger.info("[定时任务]" + cls.getName() + " 启动 at "
                    + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(startTimeMillis)) + " per "
                    + TimeConsts.formatTime(repeatMillis));
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }

            JobType job = new JobType(cls.getName(), cls, startTimeMillis, repeatMillis, jobDetail.getKey(),
                    trigger.getKey());
            mJobs.add(job);
            return job;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void modifyJobTime(JobType jobType, Long startTimeMillis, Long repeatMillis) {
        try {
            removeJob(jobType);
            addJob(jobType.cls, startTimeMillis, repeatMillis);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void removeJob(JobType jobType) {
        try {
            logger.info("[定时任务]" + jobType.jobName + " 停止");
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.pauseTrigger(jobType.triggerKey);// 停止触发器
            sched.unscheduleJob(jobType.triggerKey);// 移除触发器
            sched.deleteJob(jobType.jobKey);// 删除任务
            mJobs.remove(jobType);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void startJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.start();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdownJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
