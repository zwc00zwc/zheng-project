package common.job;

/**
 * Created by alan.zheng on 2017/1/17.
 */
public class SimpleJobExecutor extends JobExecutor {
    private final SimpleJob simpleJob;

    public SimpleJobExecutor(JobConfig jobConfig,final SimpleJob _simpleJob){
        super(jobConfig);
        simpleJob=_simpleJob;
    }

    @Override
    protected void process() {
        simpleJob.execute();
    }
}
