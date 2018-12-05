package com.lw.vcs.auth.controller;


import com.lw.vcs.auth.AuthIgnore;
import com.lw.vcs.auth.model.LoginVO;
import com.lw.vcs.auth.service.LoginService;
import com.lw.vcs.auth.service.UserService;
import com.lw.vcs.log.LogType;
import com.lw.vcs.log.SystemLog;
import com.lw.vcs.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author：lian.wei
 * @Date：2018/8/10 20:52
 * @Description：
 */
@RestController
@Api(value = "登录控制器", tags = "权限", description = "登录模块，做身份验证")
@RequestMapping("auth")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登录", notes = "权限验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "手机号", example = "18389386309"),
            @ApiImplicitParam(name = "password", value = "密码", example = "f62dd3e57b3073ef935e85f7d2e267be"),
            @ApiImplicitParam(name = "remember", value = "记住密码", example = "false")
    })
    @AuthIgnore
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(HttpServletResponse response, @Valid LoginVO loginVO) {

        return Result.success(loginService.login(response, loginVO));

    }

    @ApiOperation(value = "注销登录", notes = "权限验证")
    @AuthIgnore
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public Result<Boolean> logout(HttpServletRequest request, HttpServletResponse response) {
        loginService.logout(request, response);
        return Result.success(true);

    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "手机号", example = "18389386306"),
            @ApiImplicitParam(name = "password", value = "密码", example = "bfe05f6eb476b686cd43701a5a86504f")
    })
    @RequestMapping(value = "password", method = RequestMethod.POST)
    public Result<Boolean> password(@Valid LoginVO loginVO) {

        return Result.success(loginService.repassword(loginVO));

    }
}
