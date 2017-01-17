package jobtest;

import common.job.JobConfig;
import common.job.JobScheduler;

/**
 * Created by alan.zheng on 2017/1/17.
 */
public class JobMain {
    public static void main(String[] args){
        JobConfig jobConfig=new JobConfig("testJob",TestJob.class.getCanonicalName(),"0/5 * * * * ?");
        new JobScheduler(jobConfig).init();
    }
}
