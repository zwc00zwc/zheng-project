package reg.listener;

import job.JobRegisterManager;
import job.JobScheduleController;
import job.config.JobCommand;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import reg.base.AbstractListener;

import java.util.Date;

/**
 * Created by alan.zheng on 2017/1/20.
 */
public class JobListener extends AbstractListener {
    private final String jobName;
    public JobListener(String _jobName){
        jobName=_jobName;
    }
    @Override
    public void changed(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) {
        System.out.print("发生了监听:"+new Date().toString());
        String eventstr;
        try {
            eventstr=new String(treeCacheEvent.getData().getData());
        } catch (Exception e) {
            eventstr=null;
        }
        if (StringUtils.isNotEmpty(eventstr)){
            JobScheduleController jobScheduleController= JobRegisterManager.instance().getJobScheduleController(jobName);
            if (JobCommand.PAUSE.getCommand().equals(eventstr)){
                jobScheduleController.pauseJob();
            }
            if (JobCommand.RESUME.getCommand().equals(eventstr)){
                jobScheduleController.resumeJob();
            }
            if (JobCommand.EXECUTE.getCommand().equals(eventstr)){
                jobScheduleController.triggerJob();
            }
        }
    }
}
