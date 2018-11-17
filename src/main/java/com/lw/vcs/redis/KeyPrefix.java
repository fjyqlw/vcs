package com.lw.vcs.redis;

/**
 * @Author：lian.wei
 * @Date：2018/8/9 21:05
 * @Description：
 */
public interface KeyPrefix {
    int expireSeconds();

    String getPrefix();
}
