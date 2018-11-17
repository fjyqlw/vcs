package com.lw.vcs.redis;

/**
 * @Author：lian.wei
 * @Date：2018/8/9 21:13
 * @Description：
 */
public class UserKey extends BasePrefix {
    /**
     * 过期时间（秒）
     */
    public static int expireSeconds = 600;

    public static final String COOKIE_NAME_TOKEN="token";

    private UserKey( String prefix) {
        super(prefix);
    }
    public UserKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }
    public static UserKey token = new UserKey(expireSeconds,"tk");
    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
