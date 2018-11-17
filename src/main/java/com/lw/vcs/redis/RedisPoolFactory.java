package com.lw.vcs.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author：lian.wei
 * @Date：2018/8/8 21:58
 * @Description：
 */
@Service
public class RedisPoolFactory {


    @Autowired
    private RedisConfig redisConfig;

    @Bean
    public JedisPool jedisFactory(){
        JedisPoolConfig jc=new JedisPoolConfig();
        jc.setMaxIdle(redisConfig.getPoolMaxIdle());
        jc.setMaxTotal(redisConfig.getPoolMaxTotal());
        jc.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);

        JedisPool jp=new JedisPool(jc,redisConfig.getHost(),redisConfig.getPort(),redisConfig.getTimeout() * 1000,redisConfig.getPassword(),0);

        return jp;
    }
}
