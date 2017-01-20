package job;

import com.google.common.base.Optional;
import job.config.JobConfig;
import job.log.JobLogManager;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import reg.zookeeper.ZookeeperRegistryCenter;

import java.util.Date;

/**
 * 作业任务调度器
 * Created by alan.zheng on 2017/1/16.
 */
public class JobScheduler {
    private final JobConfig jobConfig;
    private final ZookeeperRegistryCenter zookeeperRegistryCenter;
    public JobScheduler(final JobConfig _jobConfig,ZookeeperRegistryCenter _zookeeperRegistryCenter){
        jobConfig=_jobConfig;
        zookeeperRegistryCenter=_zookeeperRegistryCenter;
    }
    /**
     * 初始化作业.
     */
    public void init() {
        JobDetail jobDetail = createJobDetail(jobConfig.getJavaClass());
        Scheduler scheduler=null;
        try {
            StdSchedulerFactory factory = new StdSchedulerFactory();
            factory.initialize();
            scheduler = factory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        JobScheduleController jobScheduleController=new JobScheduleController(scheduler,jobDetail,jobConfig.getJobName());
        jobScheduleController.scheduleJob(jobConfig.getCorn());
        JobRegisterManager.instance().addJobScheduleController(jobConfig.getJobName(),jobScheduleController);
    }

    private JobDetail createJobDetail(final String javaClass){
        JobDetail jobDetail = JobBuilder.newJob(AbstractJob.class).withIdentity(jobConfig.getJobName()).build();
        jobDetail.getJobDataMap().put("jobConfig", jobConfig);
        jobDetail.getJobDataMap().put("zookeeperRegistryCenter", zookeeperRegistryCenter);
        Optional<ElasticJob> elasticJobInstance = createElasticJobInstance();
        if (elasticJobInstance.isPresent()) {
            jobDetail.getJobDataMap().put("elasticJob", elasticJobInstance.get());
        }else {
            try {
                jobDetail.getJobDataMap().put("elasticJob",Class.forName(javaClass).newInstance());
            } catch (InstantiationException e) {
                JobLogManager.log(jobConfig.getJobName(),e.toString(),new Date());
            } catch (IllegalAccessException e) {
                JobLogManager.log(jobConfig.getJobName(),e.toString(),new Date());
            } catch (ClassNotFoundException e) {
                JobLogManager.log(jobConfig.getJobName(),e.toString(),new Date());
            }
        }
        return jobDetail;
    }

    protected Optional<ElasticJob> createElasticJobInstance() {
        return Optional.absent();
    }

    /**
     * 调度作业
     */
    public static final class AbstractJob implements Job{
        private JobConfig jobConfig;
        private ElasticJob elasticJob;
        private ZookeeperRegistryCenter zookeeperRegistryCenter;
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
           if (elasticJob instanceof SimpleJob){
               new SimpleJobExecutor(jobConfig,(SimpleJob) elasticJob,zookeeperRegistryCenter).excute();
           }
        }

        public void setElasticJob(ElasticJob elasticJob) {
            this.elasticJob = elasticJob;
        }

        public void setJobConfig(JobConfig jobConfig) {
            this.jobConfig = jobConfig;
        }

        public void setZookeeperRegistryCenter(ZookeeperRegistryCenter zookeeperRegistryCenter) {
            this.zookeeperRegistryCenter = zookeeperRegistryCenter;
        }
    }
}
