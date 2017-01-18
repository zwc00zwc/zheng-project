package domain.mapper;

import domain.model.Job.Job;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by alan.zheng on 2017/1/18.
 */
public interface JobMapper {
    List<Job> queryList();

    int insertJob(@Param("job") Job job);
}
