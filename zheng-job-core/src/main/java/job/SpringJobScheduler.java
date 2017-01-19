package job;

import com.google.common.base.Optional;
import reg.zookeeper.ZookeeperRegistryCenter;

/**
 * Created by alan.zheng on 2017/1/18.
 */
public class SpringJobScheduler extends JobScheduler {
    private final ElasticJob elasticJob;
    public SpringJobScheduler(JobConfig jobConfig, ZookeeperRegistryCenter zookeeperRegistryCenter, final ElasticJob elasticJob){
        super(jobConfig,zookeeperRegistryCenter);
        this.elasticJob=elasticJob;
    }

    @Override
    protected Optional<ElasticJob> createElasticJobInstance() {
        return Optional.fromNullable(elasticJob);
    }
}
