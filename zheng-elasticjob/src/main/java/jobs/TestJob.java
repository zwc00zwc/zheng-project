package jobs;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by XR on 2016/12/2.
 */
public class TestJob extends AbstractSimpleElasticJob {
    @Override
    public void process(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext) {
        try {
            Thread.sleep(1000);
            System.out.print(new Date().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
