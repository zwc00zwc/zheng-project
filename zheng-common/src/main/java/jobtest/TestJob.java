package jobtest;

import common.job.SimpleJob;

import java.util.Date;

/**
 * Created by alan.zheng on 2017/1/17.
 */
public class TestJob implements SimpleJob {
    public void execute() {
        Date date=new Date();
        System.out.print("现在时间"+date.toString());
    }
}
