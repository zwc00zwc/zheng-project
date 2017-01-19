package job;

import com.google.common.base.Optional;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import reg.zookeeper.ZookeeperRegistryCenter;

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
        JobScheduleController jobScheduleController=new JobScheduleController(scheduler,jobDetail,"t1");
        jobScheduleController.scheduleJob(jobConfig.getCorn());
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
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
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
