import common.lock.DistributedLock;

import java.util.Date;

/**
 * Created by XR on 2016/12/16.
 */
public class Test2 {
    public static void main(String[] args){
        while (true){
            DistributedLock lock = null;
            try {
                lock=new DistributedLock("127.0.0.1:2181","test");
                lock.lock();
                Thread.sleep(1000);
                System.out.print("Test2:"+new Date().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(lock != null)
                    lock.unlock();
            }
        }
    }
}
