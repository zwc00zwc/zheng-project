package common;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created by XR on 2016/12/22.
 */
public class Job1 implements Job {
    private String jobconfig;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.print("运行Job1"+new Date().toString());
        System.out.print(jobconfig);
    }

    public void setJobconfig(String jobconfig) {
        this.jobconfig = jobconfig;
    }
}
