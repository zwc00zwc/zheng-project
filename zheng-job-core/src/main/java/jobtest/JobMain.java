package jobtest;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alan.zheng on 2017/1/17.
 */
public class JobMain {
    public static void main(String[] args){
        //注册心跳守护
        Timer timer=new Timer();
        timer.schedule(new TestTask(),0,5000);
    }
}
