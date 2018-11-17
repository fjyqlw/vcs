package com.lw.vcs.auth.model;

import com.lw.vcs.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author：lian.wei
 * @Date：2018/8/10 20:57
 * @Description：登录参数对象
 */
public class LoginVO {
    @NotNull(message = "手机号不能为空")
    @IsMobile(required = true,message = "手机号格式错误")
    private String userId;
    @NotNull(message = "密码不能为空")
    @Length(min = 32,max = 32,message = "密码参数非法")
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
