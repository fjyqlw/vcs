package com.lw.vcs.redis;

/**
 * @Author：lian.wei
 * @Date：2018/8/9 21:07
 * @Description：
 */
public abstract class BasePrefix implements KeyPrefix{

    //默认0代表永不过期
    private int expireSeconds;
    private String prefix;

    public BasePrefix(String prefix) {
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        return String.format("%s:%s",getClass().getSimpleName(),prefix);
    }
}
