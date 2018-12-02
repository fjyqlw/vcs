package com.lw.vcs.redis;

/**
 * @Author：lian.wei
 * @Date：2018/8/9 21:13
 * @Description：
 */
public class SvnUserKey extends BasePrefix {
    /**
     * 过期时间（秒）
     */
    public static int expireSeconds = 6000;

    public static final String COOKIE_NAME_TOKEN="token";

    private SvnUserKey(String prefix) {
        super(prefix);
    }
    public SvnUserKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }
    public static SvnUserKey token = new SvnUserKey(expireSeconds,"tk");
    public static SvnUserKey getById = new SvnUserKey("id");
    public static SvnUserKey getByName = new SvnUserKey("name");
}
