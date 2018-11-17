package com.lw.vcs.auth;

import com.lw.vcs.auth.model.User;
import com.lw.vcs.auth.service.LoginService;
import com.lw.vcs.exception.GlobalExceptopn;
import com.lw.vcs.redis.UserKey;
import com.lw.vcs.result.CodeMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author：lian.wei
 * @Date：2018/9/28 21:05
 * @Description：
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthIgnore annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        } else {
            return true;
        }

        //如果有@AuthIgnore注解，则不验证token
        if (annotation != null) {
            return true;
        }

        //获取用户凭证
        String token = loginService.getToken(request);

        //token凭证为空
        if (StringUtils.isBlank(token)) {
            throw new GlobalExceptopn(CodeMsg.USER_NO_AUTH);
        }

        User user = loginService.getUserByToken(response, token);

        if (null == user) {
            throw new GlobalExceptopn(CodeMsg.AUTH_EXPIRE);
        }

        return true;
    }

}
