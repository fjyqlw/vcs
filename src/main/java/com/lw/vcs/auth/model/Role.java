package com.lw.vcs.auth.model;

/**
 * @Author：lian.wei
 * @Date：2018/9/27 23:22
 * @Description：
 */
public class Role {
    private String roleId;
    private String roleName;

    public static Role create(){
        return new Role();
    }

    public String getRoleId() {
        return roleId;
    }

    public Role setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public Role setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }
}
