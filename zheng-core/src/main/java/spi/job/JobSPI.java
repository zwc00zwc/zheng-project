package spi.job;

import domain.model.Job.Job;

import java.util.List;

/**
 * Created by alan.zheng on 2017/1/18.
 */
public interface JobSPI {
    public List<Job> queryList();

    public boolean insertJob(Job job);
}