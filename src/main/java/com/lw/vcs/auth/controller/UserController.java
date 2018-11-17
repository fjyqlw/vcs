package com.lw.vcs.auth.controller;


import com.lw.vcs.audit.model.AuditUser;
import com.lw.vcs.audit.service.AuditService;
import com.lw.vcs.auth.AuthIgnore;
import com.lw.vcs.auth.model.RegisterVO;
import com.lw.vcs.auth.model.User;
import com.lw.vcs.auth.service.UserService;
import com.lw.vcs.exception.GlobalExceptopn;
import com.lw.vcs.mail.MailService;
import com.lw.vcs.result.CodeMsg;
import com.lw.vcs.result.PageData;
import com.lw.vcs.result.Result;
import com.lw.vcs.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/8/10 20:52
 * @Description：
 */
@RestController
@Api(value = "用户管理控制器", tags = "用户管理", description = "用户模块，做用户管理、注册等")
@RequestMapping("user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private MailService mailService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @ApiOperation(value = "所有用户", notes = "所有用户")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        List<User> list = userService.list();
        PageData page1 = PageData.create().setRows(list)
                .setPage(page)
                .setTotal(list.size())
                .setPageSize(pageSize);
        return Result.success(page1);

    }

    @ApiOperation(value = "所有用户", notes = "所有用户")
    @RequestMapping(value = "list/{state}", method = RequestMethod.POST)
    public Result list(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @PathVariable(value = "state", required = true) Integer state) {
        List<User> list = userService.list(state);
        PageData page1 = PageData.create().setRows(list)
                .setPage(page)
                .setTotal(list.size())
                .setPageSize(pageSize);
        return Result.success(page1);

    }

    @ApiOperation(value = "根据userId查找用户", notes = "根据userId查找用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", example = "18389386306")
    })
    @RequestMapping(value = "get/{userId}", method = RequestMethod.POST)
    public Result get(@PathVariable("userId") String userId) {
        User user = userService.getById(userId);

        if (null == user) {
            throw new GlobalExceptopn(CodeMsg.USER_NOT_FOUND);
        }
        user.setPassword(null);
        user.setSalt(null);

        return Result.success(user);

    }

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "手机号", example = "18389386306"),
            @ApiImplicitParam(name = "userName", value = "用户姓名", example = "练威"),
            @ApiImplicitParam(name = "email", value = "邮箱地址", example = "1126028813@qq.com"),
            @ApiImplicitParam(name = "comment", value = "申请原因", example = "申请账号")
    })
    @AuthIgnore
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(RegisterVO registerVO) {
        User user = userService.getById(registerVO.getUserId());

        if (null != user) {
            throw new GlobalExceptopn(CodeMsg.USER_EXIST);
        }
        userService.save(registerVO);

        user = userService.getById(registerVO.getUserId());

        if (null == user) {
            throw new GlobalExceptopn(CodeMsg.USER_REGISTER_FAIL);
        }

        return Result.success(user);
    }

    @ApiOperation(value = "邮箱验证", notes = "验证用户邮箱，包含修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", example = "18389386306"),
            @ApiImplicitParam(name = "password", value = "密码", example = "1"),
            @ApiImplicitParam(name = "code", value = "验证码", example = "")
    })
    @AuthIgnore
    @RequestMapping(value = "validate/{userId}/{code}", method = RequestMethod.POST)
    public Result emailValidate(@PathVariable(value = "userId", required = true) String userId
            , @RequestParam(value = "password", required = true) String password
            , @PathVariable(value = "code", required = true) String code) {
        User user = userService.getById(userId);
        if (null == user) {
            throw new GlobalExceptopn(CodeMsg.USER_NOT_FOUND);
        } else {
//            auditService.createUserAudit();

            return Result.success();
        }
    }

    @ApiOperation(value = "审核注册用户", notes = "审核注册用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auditId", value = "审核id", required = true, example = ""),
            @ApiImplicitParam(name = "state", value = "状态（1:同意，0:不同意）", required = true, example = "1"),
            @ApiImplicitParam(name = "message", value = "审核意见", required = true, example = "同意")
    })
    @RequestMapping(value = "audit/{auditId}/{state}", method = RequestMethod.POST)
    public Result audit(@ApiIgnore User user,
                        @PathVariable(value = "auditId", required = true) String auditId
            , @PathVariable(value = "state", required = true) Integer state
            , @RequestParam(value = "message", required = true) String message
            , @ApiIgnore HttpServletRequest request) {

        AuditUser auditUser = auditService.getById(auditId);
        auditUser.setMessage(message);
        auditUser.setAuditUserId(user.getUserId());
        auditUser.setAuditTime(sdf.format(new Date()));
        if (auditUser == null) {
            throw new GlobalExceptopn(CodeMsg.AUDIT_ID_NO_FOUND);
        }

        if (0 == state) {
            auditUser.setState(state);
            //不同意注册
            userService.audit(auditUser.getApplyUserId(), state, auditUser);
        } else if (1 == state) {
            auditUser.setState(state);
            //同意注册，状态改为待激活
            state = 2;
            userService.audit(auditUser.getApplyUserId(), state, auditUser);
        } else {
            throw new GlobalExceptopn(CodeMsg.BIND_ERROR.fileArgs("state只能是0或者1"));
        }

        User applyUser = userService.getById(auditUser.getApplyUserId());
        if (null == user) {
            throw new GlobalExceptopn(CodeMsg.USER_NOT_FOUND);
        } else if (state.equals(applyUser.getState())) {
            mailService.sendRegisterAuditEmail(applyUser, auditUser, request);
            return Result.success(true);
        } else {
            throw new GlobalExceptopn(CodeMsg.AUDIT_USER_FAILD);
        }
    }

    @ApiOperation(value = "验证链接有效性", notes = "验证链接有效性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auditId", value = "审核id", required = true, example = ""),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, example = "")
    })
    @AuthIgnore
    @RequestMapping(value = "emailValidate/{auditId}/{code}", method = RequestMethod.POST)
    public Result emailValidate(
            @PathVariable(value = "auditId", required = true) String auditId
            , @PathVariable(value = "code", required = true) String code) {

        AuditUser auditUser = auditService.validate(auditId, code);
        if (auditUser == null) {
            return Result.success(CodeMsg.AUDIT_URL_ERROR.getMsg());
        }

        User applyUser = userService.getById(auditUser.getApplyUserId());
        if (null == applyUser) {
            return Result.success(CodeMsg.USER_NOT_FOUND.getMsg());
        } else {
            return Result.success(true);
        }
    }


    @ApiOperation(value = "设置密码", notes = "设置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auditId", value = "审核id", required = true, example = ""),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, example = ""),
            @ApiImplicitParam(name = "p", value = "密码", required = true, example = "")
    })
    @AuthIgnore
    @PostMapping("password/{auditId}/{code}")
    public Result setPassword(
            @PathVariable(value = "auditId", required = true) String auditId
            , @PathVariable(value = "code", required = true) String code
            , @RequestParam(value = "p", required = true) String password) {

        AuditUser auditUser = auditService.validate(auditId, code);
        if (auditUser == null) {
            throw new GlobalExceptopn(CodeMsg.AUDIT_URL_ERROR);
        }

        User user = userService.getById(auditUser.getApplyUserId());

        if (null == user) {
            throw new GlobalExceptopn(CodeMsg.USER_NOT_FOUND);
        }

        String salt = MD5Util.getSalt();
        password = MD5Util.formPass2DBPass(password,salt);
        userService.active(user.getUserId(),password,salt,auditId);

        return Result.success(user);

    }
}
