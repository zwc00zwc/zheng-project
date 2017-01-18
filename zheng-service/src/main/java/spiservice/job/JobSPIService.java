package spiservice.job;

import common.reg.zookeeper.ZookeeperConfig;
import common.reg.zookeeper.ZookeeperRegistryCenter;
import domain.manager.JobManager;
import domain.model.Job.Job;
import domain.model.Job.JobLog;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spi.job.JobSPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan.zheng on 2017/1/18.
 */
@Service("jobSPIService")
public class JobSPIService implements JobSPI {
    @Autowired
    private JobManager jobManager;
    public List<Job> queryList() {
        List<Job> jobs= jobManager.queryList();
        ZookeeperConfig zookeeperConfig=new ZookeeperConfig();
        zookeeperConfig.setServerLists("127.0.0.1:2181");
        zookeeperConfig.setNamespace("root");
        zookeeperConfig.setAuth("auth");
        ZookeeperRegistryCenter zookeeperRegistryCenter= null;
        try {
            zookeeperRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        zookeeperRegistryCenter.init();
        CuratorFramework curatorFramework=(CuratorFramework) zookeeperRegistryCenter.getRawClient();
        if (jobs!=null&&jobs.size()>0){
            for (Job job:jobs) {
                if (zookeeperRegistryCenter.isExisted("/"+job.getJobName()+"")){
                    job.setStatus("正在运行");
                }else {
                    job.setStatus("已停止");
                }
            }
        }
        return jobs;
    }

    public boolean insertJob(Job job) {
        return jobManager.insertJob(job);
    }

    public List<JobLog> queryJobLogList() {
        List<JobLog> logs=new ArrayList<JobLog>();
        return logs;
    }
}
