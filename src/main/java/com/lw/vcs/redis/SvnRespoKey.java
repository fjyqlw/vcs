package com.lw.vcs.redis;

/**
 * @Author：lian.wei
 * @Date：2018/8/9 21:13
 * @Description：
 */
public class SvnRespoKey extends BasePrefix {

    /**
     * 过期时间（秒）
     * 1小时后过期
     */
    public static int expireSeconds = 3600;

    private SvnRespoKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }

    public static SvnRespoKey getById = new SvnRespoKey(expireSeconds,"id");
}
