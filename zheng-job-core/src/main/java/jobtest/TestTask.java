package jobtest;

import java.util.TimerTask;

/**
 * Created by alan.zheng on 2017/2/13.
 */
public class TestTask extends TimerTask {
    @Override
    public void run() {
        System.out.print("测试中");
    }
}
