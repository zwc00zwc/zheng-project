package lock;

import common.lock.RedisLock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alan.zheng on 2018/3/14.
 */
@Aspect
@Component
public class RedisLockService implements Ordered {

    private Logger logger = LoggerFactory.getLogger(RedisLockService.class);

    public int getOrder() {
        //设置比事务排序前
        return 1;
    }

    @Autowired
    private RedisLock redisLock;

    @Pointcut("@annotation(com.yourong.core.lock.Lock)")
    public void methodPointcut(){

    }

    @Before("@annotation(lock)")
    public void before(JoinPoint point, Lock lock) throws Exception {
        try {
            String aClass = point.getTarget().getClass().getName();
            String method =  point.getSignature().getName();
            Map map = new HashMap();
            map.put("class",aClass);
            map.put("method",method);
//            logger.debug("进入切面,当前切点类为"+point.getTarget().getClass()+",方法名为+"+point.getSignature().getName()+"");
            redisLock.lock(map);
        } catch (Exception e) {
            throw e;
        }
    }

    @After("methodPointcut()")
    public void after(JoinPoint point){
        String aClass = point.getTarget().getClass().getName();
        String method =  point.getSignature().getName();
        Map map = new HashMap();
        map.put("class",aClass);
        map.put("method",method);
//        logger.debug("离开切面当前切点类为"+point.getTarget().getClass()+",方法名为+"+point.getSignature().getName()+"");
        redisLock.unlock(map);
    }
}
