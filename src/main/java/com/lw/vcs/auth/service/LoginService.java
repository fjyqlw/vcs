package com.lw.vcs.auth.service;

import com.lw.vcs.auth.mapper.UserDao;
import com.lw.vcs.auth.model.ConstantData;
import com.lw.vcs.auth.model.LoginVO;
import com.lw.vcs.auth.model.User;
import com.lw.vcs.exception.GlobalExceptopn;
import com.lw.vcs.redis.RedisService;
import com.lw.vcs.redis.SvnUserKey;
import com.lw.vcs.redis.UserKey;
import com.lw.vcs.result.CodeMsg;
import com.lw.vcs.svnkit.model.SvnUserCfg;
import com.lw.vcs.svnkit.service.SvnOptService;
import com.lw.vcs.utils.MD5Util;
import com.lw.vcs.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author：lian.wei
 * @Date：2018/8/7 22:12
 * @Description：
 */
@Service
public class LoginService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private SvnOptService svnOptService;

    @Autowired
    private RedisService redisService;
    private int second_day_3 = 60 * 60 * 24 * 3;


    public User login(HttpServletResponse response, LoginVO loginVO) {
        User user = userDao.getById(loginVO.getUserId());
        user.setUserId(user.getUserId());
        if (user == null) {
            throw new GlobalExceptopn(CodeMsg.MOBILE_NOT_FOUND);
        }

        switch (user.getState()) {
            case ConstantData.USER_NO_AUDIT:
                throw new GlobalExceptopn(CodeMsg.USER_NO_AUDIT);
            case ConstantData.USER_FORBIDDEN:
                throw new GlobalExceptopn(CodeMsg.USER_FORBIDDEN);
            case ConstantData.USER_NO_ACTIVATED:
                throw new GlobalExceptopn(CodeMsg.USER_NO_ACTIVATED);
        }

        String dbPass = user.getPassword();
        String formPass = loginVO.getPassword();
        String dbSalt = user.getSalt();

        String dbPass2 = MD5Util.formPass2DBPass(formPass, dbSalt);

        if (!dbPass.equals(dbPass2)) {
            throw new GlobalExceptopn(CodeMsg.PASSWORD_ERROR);
        }
        int second = UserKey.expireSeconds;
        //记住密码3天
        if(loginVO.getRemember()) {
            second = second_day_3;
            user.setRemember(true);
        }

        String token = UUIDUtil.getUUID();
        addCookie(response, token, user, second);

        user.setToken(token)
        .setNoAuthData();
        return user;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = getToken(request);

        if (StringUtils.isBlank(token)) {
            throw new GlobalExceptopn(CodeMsg.USER_NO_AUTH);
        }

        //设置过期时间为0
        Long a = redisService.expire(UserKey.token, token, 0);

        expireCookie(response);
    }

    public User getUserByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) return null;

        User user = redisService.get(UserKey.token, token, User.class);

        if(user.getRemember()) {
            Long ttl = redisService.ttl(UserKey.token, token);
            if(ttl < UserKey.expireSeconds) {
                addCookie(response, token, user,UserKey.expireSeconds);
            }
        }

        return user;
    }

    /**
     * 获取token
     *
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {

        String token = request.getHeader(UserKey.COOKIE_NAME_TOKEN);

        if (StringUtils.isBlank(token)) {
            token = request.getParameter(UserKey.COOKIE_NAME_TOKEN);
        }
        if (StringUtils.isBlank(token)) {
            Object obj = request.getAttribute(UserKey.COOKIE_NAME_TOKEN);
            if (null != obj) {
                token = obj.toString();
            }
        }
        if (StringUtils.isBlank(token)) {
            token = getCoolieValue(request, UserKey.COOKIE_NAME_TOKEN);
        }

        return token;
    }

    private void addCookie(HttpServletResponse response, String token, User user,int second) {
        redisService.set(UserKey.token, token, user,second);

        Cookie cookie = new Cookie(UserKey.COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    private void expireCookie(HttpServletResponse response) {

        Cookie cookie = new Cookie(UserKey.COOKIE_NAME_TOKEN, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public String getCoolieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieNameToken.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public boolean repassword(LoginVO loginVO) {
        return true;
    }
}
