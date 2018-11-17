package com.lw.vcs.config;

import com.lw.vcs.auth.model.User;
import com.lw.vcs.auth.service.LoginService;
import com.lw.vcs.auth.service.UserService;
import com.lw.vcs.exception.GlobalExceptopn;
import com.lw.vcs.redis.UserKey;
import com.lw.vcs.result.CodeMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author：lian.wei
 * @Date：2018/8/11 15:55
 * @Description：
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private LoginService loginService;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(UserKey.COOKIE_NAME_TOKEN);
        String cookieToken = getCoolieValue(request,UserKey.COOKIE_NAME_TOKEN);

        String token = null;
        if (StringUtils.isNotBlank(paramToken)) {
            token = paramToken;
        } else if (StringUtils.isNotBlank(cookieToken)) {
            token = cookieToken;
        } else {
            throw new GlobalExceptopn(CodeMsg.USER_NO_AUTH);
        }

        return loginService.getUserByToken(response,token);

    }

    private String getCoolieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies =request.getCookies();

        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookieNameToken.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
