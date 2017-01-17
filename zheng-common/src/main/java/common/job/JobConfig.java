package common.job;

/**
 * Created by alan.zheng on 2017/1/17.
 */
public class JobConfig {
    public JobConfig(String _jobName,String _javaClass,String _corn){
        jobName=_jobName;
        javaClass=_javaClass;
        corn=_corn;
    }
    private final String jobName;

    private final String javaClass;

    private final String corn;
    public String getJobName() {
        return jobName;
    }

    public String getJavaClass() {
        return javaClass;
    }

    public String getCorn() {
        return corn;
    }
}
