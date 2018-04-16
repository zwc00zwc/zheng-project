package common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/21.
 */
public class RedisManager {
    private static Logger logger = LoggerFactory.getLogger(RedisManager.class);
    private static JedisPool jedisPool;
    public static JedisPool getJedisPool(){
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        jedisPoolConfig.setMaxIdle(5);
        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(-1);
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPool=new JedisPool(jedisPoolConfig,"192.168.48.129",6379);
        return jedisPool;
    }

    public static Jedis getClient(){
        if (jedisPool==null){
            getJedisPool();
        }
        return jedisPool.getResource();
    }

    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    public static String get(String key) {
        Jedis jedis = null;
        String value = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = getClient();
            value = jedis.get(key);
        } catch (JedisConnectionException e) {
            logger.error("redis,获取 key={}的数据" , key, e);
            borrowOrOprSuccess = false;
            if (jedis != null)
                returnResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                returnResource(jedis);
        }
        if (logger.isDebugEnabled())
            logger.debug("getObject:" + key.hashCode() + "=" + value);
        return value;
    }

    public static void del(String key) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = getClient();
            jedis.del(key);
        } catch (JedisConnectionException e) {
            logger.error("redis,删除 key=" + key, e);
            borrowOrOprSuccess = false;
            if (jedis != null)
                returnResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                returnResource(jedis);
        }

    }

    public static boolean exists(String key) {
        Jedis jedis = null;
        boolean value = false;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = getClient();
            value = jedis.exists(key);
        } catch (JedisConnectionException e) {
            logger.error("查询redis,失败key=" + key, e);
            borrowOrOprSuccess = false;
            if (jedis != null)
                returnResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                returnResource(jedis);
        }
        return value;
    }

    public static long setnx(String key, String xvalue) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        long result = 0l;
        try {
            jedis = getClient();
            result = jedis.setnx(key, xvalue);
        } catch (JedisConnectionException e) {
            logger.error("set redis,失败key=" + key, e);
            borrowOrOprSuccess = false;
            if (jedis != null)
                returnResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                returnResource(jedis);
        }
        return result;
    }

    public static Long expire(String key, int seconds) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        Long result = 0L;
        try {
            jedis = getClient();
            result = jedis.expire(key, seconds);
        } catch (JedisConnectionException e) {
            logger.error("hset redis,失败key=" + key, e);
            borrowOrOprSuccess = false;
            if (jedis != null)
                returnResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                returnResource(jedis);
        }
        return result;
    }

    public static Object getObject(String key){
        Jedis jedis = null;
        Object value = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = getClient();
//            jedis.select(PropertiesUtil.getObjectCacheDBIndex());
            value = SerializationUtils.deserialize(jedis.get(SerializationUtils
                    .serialize(key.hashCode())));
        } catch (JedisConnectionException e) {
//            logger.error("redis,获取 key={}的数据" , key, e);
            borrowOrOprSuccess = false;
            if (jedis != null)
                jedisPool.returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                jedisPool.returnResource(jedis);
        }
//        if (logger.isDebugEnabled())
//            logger.debug("getObject:" + key.hashCode() + "=" + value);
        return value;
    }

    public static boolean isExitByObjectKey(Object key) {
        Jedis jedis = null;
        boolean value = false;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = getClient();
//            jedis.select(PropertiesUtil.getObjectCacheDBIndex());
            value = jedis.exists(SerializationUtils.serialize(key.hashCode()));
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                jedisPool.returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                jedisPool.returnResource(jedis);
        }
//        if (logger.isDebugEnabled())
//            logger.debug("getObject:" + key.hashCode() + "=" + value);
        return value;
    }

    public static void putObject(Object key, Object value) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        Serializable serializeble = null;
        if (value instanceof java.io.Serializable) {
            serializeble = (Serializable) value;
        } else {
//            logger.error("对象未序列化");
            return;
        }
        try {
            jedis = getClient();
            jedisPool = RedisManager.getJedisPool();
            jedis.set(SerializationUtils.serialize(key.hashCode()), SerializationUtils.serialize(serializeble));
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                jedisPool.returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                jedisPool.returnResource(jedis);
        }

    }

    public static Object removeObject(Object key) {
        Jedis jedis = null;
        Object value = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = getClient();
            value = jedis.expire(SerializationUtils.serialize(key.hashCode()), 0);
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                jedisPool.returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                jedisPool.returnResource(jedis);
        }
//        if (logger.isDebugEnabled())
//            logger.debug("getObject:" + key.hashCode() + "=" + value);
        return value;
    }
}
