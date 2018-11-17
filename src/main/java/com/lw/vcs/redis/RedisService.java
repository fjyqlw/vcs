package com.lw.vcs.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author：lian.wei
 * @Date：2018/8/8 21:05
 * @Description：
 */
@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取单个对象
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix,String key,Class<T> clazz){
        Jedis jedis = null;

        try{
            jedis = jedisPool.getResource();

            String realKey = prefix.getPrefix() + key;
            String value = jedis.get(realKey);

            if(value == null || value.length() <= 0){
                return null;
            }

            T t = stringToBean(value,clazz);
            return t;
        }finally{
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis = null;

        try{
            jedis = jedisPool.getResource();
            String valueStr = beanToString(value);

            if(null == valueStr || valueStr.length() <= 0){
                return false;
            }

            String realKey = prefix.getPrefix() + key;
            int second = prefix.expireSeconds();

            if(second <= 0){
                jedis.set(realKey,valueStr);
            }else{
                jedis.setex(realKey,second,valueStr);
            }

            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key是否存在
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exists(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;

            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long incr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;

           return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long decr(KeyPrefix prefix,String key){
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;

            return jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long expire(KeyPrefix prefix,String key,int expireSeconds){
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;

            return jedis.expire(realKey,expireSeconds);
        }finally {
            returnToPool(jedis);
        }
    }
    private <T> String beanToString(T value) {
        if(null == value){
            return null;
        }

        Class<?> clazz = value.getClass();

        if(clazz == int.class || clazz == Integer.class){
            return String.valueOf(value);
        } else if(clazz == long.class || clazz == Long.class){
            return String.valueOf(value);
        } else if(clazz == String.class){
            return (String)value;
        }else {
            return JSON.toJSONString(value);
        }

    }

    private <T> T stringToBean(String value, Class<T> clazz) {
        if(null == value || value.length() <= 0 || null == clazz){
            return null;
        }

        if(clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(value);
        } else if(clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(value);
        } else if(clazz == String.class){
            return (T)value;
        }else {
            return JSON.toJavaObject(JSON.parseObject(value),clazz);
        }
    }



    private void returnToPool(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }
}
