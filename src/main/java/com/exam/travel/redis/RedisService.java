package com.exam.travel.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author w1586
 * @Date 2020/3/3 3:04
 * @Cersion 1.0
 */
@Service
@Slf4j
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    /**
     * 设置对象
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null || str.length() <= 0) {
                return false;
            }
            // 生成真正的key，也就是realKey
            String realKey = prefix.getPrefix() + key;
            //对过期时间进行操作
            int seconds = prefix.expireSeconds();
            if (seconds<=0){
                jedis.set(realKey, str);
            }else {
                jedis.setex(realKey, seconds, str);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnToPool(jedis);
            return true;
        }
    }

    /**
     * 获取单个对象
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        T t = null;
        try {
            jedis = jedisPool.getResource();
            // 生成真正的key，也就是realKey
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            t = stringToBean(str, clazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnToPool(jedis);
            return t;
        }
    }


    /**
     * 判断key是否存在
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 删除
     */
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            long ret = jedis.del(realKey);
            return ret > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    public boolean delete(KeyPrefix prefix) {
        if(prefix == null) {
            return false;
        }
        List<String> keys = scanKeys(prefix.getPrefix());
        if(keys==null || keys.size() <= 0) {
            return true;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(keys.toArray(new String[0]));
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    public List<String> scanKeys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> keys = new ArrayList<String>();
            String cursor = "0";
            ScanParams sp = new ScanParams();
            sp.match("*"+key+"*");
            sp.count(100);
            do{
                ScanResult<String> ret = jedis.scan(cursor, sp);
                List<String> result = ret.getResult();
                if(result!=null && result.size() > 0){
                    keys.addAll(result);
                }
                //再处理cursor
//                cursor = ret.getStringCursor();
                cursor = ret.getCursor();
            }while(!cursor.equals("0"));
            return keys;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 增加值
     */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

//    public Long del(String key) {
//        Jedis jedis = null;
//        Long result = null;
//        try {
//            jedis = jedisPool.getResource();
//            result = jedis.del(key);
//        } catch (Exception e) {
//            log.error("del key:{} error", key, e);
//            jedisPool.returnBrokenResource(jedis);
//            return result;
//        }
//        jedisPool.returnResource(jedis);
//        return result;
//    }







//    /**
//     * 设置失效时间
//     *
//     * @param key
//     * @param value
//     * @return
//     */
//    public Long setnx(String key, String value) {
//        Jedis jedis = null;
//        Long result = null;
//        try {
//            jedis = jedisPool.getResource();
//            result = jedis.setnx(key, value);
//        } catch (Exception e) {
//            log.error("expire key:{} error", key, e);
//            jedisPool.returnResource(jedis);
//            return result;
//        }
//        jedisPool.returnResource(jedis);
//        return result;
//
//    }

//    /**
//     * 设置key的有效期，单位是秒
//     *
//     * @param key
//     * @param exTime
//     * @return
//     */
//    public Long expire(String key, int exTime) {
//        Jedis jedis = null;
//        Long result = null;
//        try {
//            jedis = jedisPool.getResource();
//            result = jedis.expire(key, exTime);
//        } catch (Exception e) {
//            log.error("expire key:{} error", key, e);
//            jedisPool.returnBrokenResource(jedis);
//            return result;
//        }
//        jedisPool.returnResource(jedis);
//        return result;
//    }


    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    @SuppressWarnings("unchecked")
    public static  <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    public static  <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }

    }

}
