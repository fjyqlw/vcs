package com.lw.vcs.auth.model;

/**
 * @Author：lian.wei
 * @Date：2018/8/7 22:08
 * @Description：
 */
public class User {
    /*用户id,手机号码*/
    private String userId;
    private String email;
    /*用户姓名*/
    private String userName;
    /*MD5(MD5(pass明文+固定salt)+salt)*/
    private String password;
    private String salt;
    /*头像，云存储的id*/
    private String head;
    /*注册时间*/
    private String registerDate;
    /*上次登录时间*/
    private String lastLoginDate;
    /*登录次数*/
    private String loginCount;
    /**
     * 0:未审核
     * 1:正常
     * 2:未激活
     * 3:已禁用
     */
    private Integer state;

    private String token;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(String loginCount) {
        this.loginCount = loginCount;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    public User setNoAuthData() {
        this.salt = null;
        this.password = null;
        return this;
    }
}
