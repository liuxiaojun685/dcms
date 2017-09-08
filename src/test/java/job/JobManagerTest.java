package job;

import cn.bestsec.dcms.platform.utils.task.JobManager;
import cn.bestsec.dcms.platform.utils.task.JobManager.JobType;
import cn.bestsec.dcms.platform.utils.task.MailAlarmJob;

/**
* @author bada email:bada@bestsec.cn
* @time 2017年2月20日 下午1:23:39
*/
public class JobManagerTest {

    public static void main(String[] args) {
        JobManager mg = JobManager.getInstance();
        System.out.println("==============addJob");
        JobType a = mg.addJob(MailAlarmJob.class, System.currentTimeMillis() + 5000, 1000L);
//        if (mg.checkExist(UserUnlockJob.class)) {
//            mg.modifyJobTime(mg.getJobType(UserUnlockJob.class), new Date().getTime(), 3000L);
//        }
//        mg.addJob(BackupJob.class, new Date().getTime(), 2000L);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("==============modifyJobTime");
        mg.modifyJobTime(a, -1L, 3000L);
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mg.shutdownJobs();
    }
}
