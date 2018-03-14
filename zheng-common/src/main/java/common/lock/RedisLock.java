package common.lock;

import common.redis.RedisManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by alan.zheng on 2018/3/14.
 */
public class RedisLock {
    private ThreadLocal<Map> keyLocalValue = new ThreadLocal<Map>();

    private static Logger logger = LoggerFactory.getLogger(DistributedLock.class);
    private final String lock_key = "lock_foo";

    private final Integer lock_expire = 10;

    public RedisLock(){

    }

    public void lock(Map map) throws Exception{
        try {
            if (tryLock(map)){
                logger.info(Thread.currentThread().toString()+"获得锁");
                return;
            }else {
                waitForLock(map);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 加锁
     */
    public void lock() throws Exception{
        try {
            StackTraceElement element = Thread.currentThread().getStackTrace()[1];
            Map map = new HashMap();
            map.put("class",element.getClassName());
            map.put("method",element.getMethodName());
            if (tryLock(map)){
                logger.info(Thread.currentThread().toString()+"获得锁");
                return;
            }else {
                waitForLock(map);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 尝试加锁
     * @return
     */
    public boolean tryLock(Map map) throws Exception{
        if (keyLocalValue.get()!=null){
            return true;
        }
        //加锁成功
        String identifier = UUID.randomUUID().toString();
        try {
            if (RedisManager.setnx(lock_key,identifier)>0){
                map.put("value",identifier);
                keyLocalValue.set(map);
                //设置key有效期
                RedisManager.expire(lock_key,lock_expire);
                return true;
            }
        } catch (Exception e) {
            logger.error("操作redis异常",e);
            throw e;
        }
        return false;
    }

    /**
     * 释放锁
     */
    public void unlock(Map map) {
        logger.debug("---------释放锁-----------");
        Map kvmap = keyLocalValue.get();

        if (kvmap == null || !kvmap.containsKey("class") || !kvmap.containsKey("method")){
            return;
        }
        if (map == null || !map.containsKey("class") || !map.containsKey("method")){
            return;
        }

        String aclass = kvmap.get("class")+"";
        String amethod = kvmap.get("method")+"";
        String bclass = map.get("class")+"";
        String bmethod = map.get("method")+"";

        if (aclass.equals(bclass) && amethod.equals(bmethod)){
            remove();
            return;
        }
    }

    /**
     * 释放锁
     */
    public void unlock() {
        logger.debug("---------释放锁-----------");
        StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        Map kvmap = keyLocalValue.get();

        if (kvmap == null || !kvmap.containsKey("class") || !kvmap.containsKey("method")){
            return;
        }

        String aclass = kvmap.get("class")+"";
        String amethod = kvmap.get("method")+"";
        String bclass = element.getClassName();
        String bmethod = element.getMethodName();

        if (aclass.equals(bclass) && amethod.equals(bmethod)){
            remove();
            return;
        }
    }

    private void remove(){
        if (RedisManager.exists(lock_key)){
            String lock_value = RedisManager.get(lock_key);
            //判断redis 值是否为当前线程插入值,进行删除key操作，否则进行自动释放锁
            Map map = keyLocalValue.get();
            if (StringUtils.isNotEmpty(lock_value)){
                if (map!=null && map.containsKey("value")){
                    if (lock_value.equals(map.get("value")+"")){
                        keyLocalValue.remove();
                        RedisManager.del(lock_key);
                    }
                }
            }
        }
    }

    /**
     * 等待锁
     * @return
     */
    private boolean waitForLock(Map map) throws Exception{
        int a = 50;
        while (a>0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a--;
            //尝试加锁
            logger.debug("--------尝试获取锁-----------");
            if (tryLock(map)){
                return true;
            }
        }
        logger.error(Thread.currentThread().toString() + "获取锁超时");
        throw new Exception("尝试获取锁超时");
    }
}
