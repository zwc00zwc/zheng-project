package common.job;

import com.google.common.base.Optional;

/**
 * Created by alan.zheng on 2017/1/18.
 */
public class SpringJobScheduler extends JobScheduler {
    private final ElasticJob elasticJob;
    public SpringJobScheduler(JobConfig jobConfig,final ElasticJob elasticJob){
        super(jobConfig);
        this.elasticJob=elasticJob;
    }

    @Override
    protected Optional<ElasticJob> createElasticJobInstance() {
        return Optional.fromNullable(elasticJob);
    }
}
