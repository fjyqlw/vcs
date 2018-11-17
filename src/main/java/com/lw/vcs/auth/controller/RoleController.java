package com.lw.vcs.auth.controller;

import com.lw.vcs.auth.model.Role;
import com.lw.vcs.auth.service.RoleService;
import com.lw.vcs.exception.GlobalExceptopn;
import com.lw.vcs.result.CodeMsg;
import com.lw.vcs.result.PageData;
import com.lw.vcs.result.Result;
import com.lw.vcs.utils.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/27 20:35
 * @Description：
 */
@RestController
@Api(value = "角色管理控制器", tags = "角色管理", description = "角色模块，做角色管理、用户角色配置等")
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "新建角色", notes = "新建角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", example = "新建角色")
    })
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Result create(@RequestParam("roleName") String roleName) {
        Role role = Role.create().setRoleId(UUIDUtil.getUUID())
                .setRoleName(roleName);
        roleService.insert(role);

        if (null == roleService.getById(role.getRoleId())) {
            throw new GlobalExceptopn(CodeMsg.ROLE_NOT_FOUND);
        }

        return Result.success(role);

    }

    @ApiOperation(value = "根据roleId查找角色", notes = "根据roleId查找角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "用户id", example = "18389386306")
    })
    @RequestMapping(value = "get/{roleId}", method = RequestMethod.POST)
    public Result get(@PathVariable("roleId") String roleId) {
        Role role = roleService.getById(roleId);

        if (null == role) {
            throw new GlobalExceptopn(CodeMsg.ROLE_NOT_FOUND);
        }

        return Result.success(role);

    }


    @ApiOperation(value = "查询所有角色", notes = "查询所有角色")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        List<Role> roleList = roleService.list();
        PageData pageData = PageData.create()
                .setRows(roleList)
                .setPage(page)
                .setTotal(roleList.size())
                .setPageSize(pageSize);

        return Result.success(pageData);

    }
}
