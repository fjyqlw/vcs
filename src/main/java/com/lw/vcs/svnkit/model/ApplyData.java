package com.lw.vcs.svnkit.model;

/**
 * @Author：lian.wei
 * @Date：2018/11/28 21:00
 * @Description：申请信息
 */
public class ApplyData {
    private String applyId;
    private String applyUserId;
    private String type;
    private String name;
    private String url;
    private String comment;
    private String fileId;
    private String applyTime;
    private String auditUserId;

    public static ApplyData create(){
        return new ApplyData();
    }

    public String getApplyId() {
        return applyId;
    }

    public ApplyData setApplyId(String applyId) {
        this.applyId = applyId;
        return this;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public ApplyData setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
        return this;
    }

    public String getType() {
        return type;
    }

    public ApplyData setType(String type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public ApplyData setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ApplyData setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public ApplyData setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getFileId() {
        return fileId;
    }

    public ApplyData setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public ApplyData setApplyTime(String applyTime) {
        this.applyTime = applyTime;
        return this;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public ApplyData setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
        return this;
    }
}
