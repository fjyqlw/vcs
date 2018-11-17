package com.lw.vcs.audit.model;

/**
 * @Author：lian.wei
 * @Date：2018/10/7 20:27
 * @Description：审核实体类
 */
public class Audit {
    private String auditId;
    private String applyUserId;
    private String createTime;
    private String auditTime;
    private Integer state;
    private String auditUserId;
    private String comment;
    private String message;

    public static Audit create() {
        return new Audit();
    }

    public String getAuditId() {
        return auditId;
    }

    public Audit setAuditId(String auditId) {
        this.auditId = auditId;
        return this;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public Audit setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Audit setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public Audit setState(Integer state) {
        this.state = state;
        return this;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public Audit setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Audit setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Audit setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }
}
