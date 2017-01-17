package common.job;

/**
 * Created by alan.zheng on 2017/1/17.
 */
public class SimpleJobExecutor extends JobExecutor {
    private final SimpleJob simpleJob;

    public SimpleJobExecutor(final SimpleJob _simpleJob){
        simpleJob=_simpleJob;
    }

    @Override
    protected void process() {
        simpleJob.execute();
    }
}
