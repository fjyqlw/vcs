package com.lw.vcs.auth.model;

import com.lw.vcs.validator.IsMobile;

import javax.validation.constraints.NotNull;

/**
 * @Author：lian.wei
 * @Date：2018/9/27 20:15
 * @Description：
 */

public class RegisterVO {

    /*用户id,手机号码*/
    @NotNull(message = "手机号不能为空")
    @IsMobile(required = true,message = "手机号格式错误")
    private String userId;
    private String email;
    /*用户姓名*/
    private String userName;
    /*MD5(MD5(pass明文+固定salt)+salt)*/
    @NotNull(message = "密码不能为空")
    private String password;
    /*头像，云存储的id*/
    private String head;
    /*注册时间*/
    private String registerDate;
    /*上次登录时间*/
    private String lastLoginDate;
    /*注释*/
    private String comment;

    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
