package domain.mapper;

import domain.model.Job.Job;
import domain.model.Job.query.JobQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by alan.zheng on 2017/1/18.
 */
public interface JobMapper {
    List<Job> queryList();

    List<Job> queryPageList(@Param("query") JobQuery query);

    int queryCountPage(@Param("query") JobQuery query);

    int insertJob(@Param("job") Job job);
}
