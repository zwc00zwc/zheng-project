package common.job;

import com.google.common.base.Optional;
import common.Job1;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 作业任务调度器
 * Created by alan.zheng on 2017/1/16.
 */
public class JobScheduler {
    private final String jobName;
    private final String javaClass;
    private final String corn;
    public JobScheduler(final JobConfig jobConfig){
        jobName=jobConfig.getJobName();
        javaClass=jobConfig.getJavaClass();
        corn=jobConfig.getCorn();
    }
    /**
     * 初始化作业.
     */
    public void init() {
        JobDetail jobDetail = createJobDetail(javaClass);
        Scheduler scheduler=null;
        try {
            StdSchedulerFactory factory = new StdSchedulerFactory();
            factory.initialize();
            scheduler = factory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        JobScheduleController jobScheduleController=new JobScheduleController(scheduler,jobDetail,"t1");
        jobScheduleController.scheduleJob(corn);
    }

    private JobDetail createJobDetail(final String javaClass){
        JobDetail jobDetail = JobBuilder.newJob(AbstractJob.class).withIdentity(jobName).build();
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
        private ElasticJob elasticJob;
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
           if (elasticJob instanceof SimpleJob){
               new SimpleJobExecutor((SimpleJob) elasticJob).process();
           }
        }

        public void setElasticJob(ElasticJob elasticJob) {
            this.elasticJob = elasticJob;
        }
    }
}
