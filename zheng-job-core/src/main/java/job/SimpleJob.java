package job;

/**
 * Created by alan.zheng on 2017/1/17.
 */
public interface SimpleJob extends ElasticJob {

    /**
     * 作业执行业务
     */
    void execute();
}
