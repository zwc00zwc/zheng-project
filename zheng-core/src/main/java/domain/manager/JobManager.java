package domain.manager;

import common.page.PageModel;
import domain.mapper.JobMapper;
import domain.model.Job.Job;
import domain.model.Job.JobLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by alan.zheng on 2017/1/18.
 */
@Transactional
@Component
public class JobManager {
    @Autowired
    private JobMapper jobMapper;
    public List<Job> queryList(){
        return jobMapper.queryList();
    }

    public boolean insertJob(Job job){
        if (jobMapper.insertJob(job)>0){
            return true;
        }
        return false;
    }

    public PageModel<JobLog> queryJobLogList(){

        return null;
    }
}
