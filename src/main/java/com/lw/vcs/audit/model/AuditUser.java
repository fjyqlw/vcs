package com.lw.vcs.audit.model;

/**
 * @Author：lian.wei
 * @Date：2018/10/7 20:27
 * @Description：审核实体类
 */
public class AuditUser extends Audit{
    private String code;


    public static AuditUser create() {
        return new AuditUser();
    }

    public String getCode() {
        return code;
    }

    public AuditUser setCode(String code) {
        this.code = code;
        return this;
    }
}
