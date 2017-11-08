package zheng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by alan.zheng on 2017/11/8.
 */
public class ApplicationMain {
    public static void main(String[] args){
        System.out.print("start zheng main");
        Thread thread = new WatchTherad();
        thread.start();
    }
}

class WatchTherad extends Thread{
    private static Logger logger = LoggerFactory.getLogger(WatchTherad.class);

    private String[] xmlApplicationContexts = new String[] {
            "classpath*:applicationContext.xml"
    };

    //spring centex
    private ClassPathXmlApplicationContext context = null;
    @Override
    public synchronized void start() {
        if (context == null || !context.isRunning()) {
            logger.info("Start yourong-listener-gift ...");
            //加载spring
            context = new ClassPathXmlApplicationContext(xmlApplicationContexts);
            context.start();
        }
        super.start();
    }

    public synchronized void shutdown() {
        if (context!=null && context.isRunning()) {
            logger.info("Stop yourong-listener-gift ...");
            context.stop();
            context.destroy();
        }
    }


    @Override
    public void run() {

    }
}
