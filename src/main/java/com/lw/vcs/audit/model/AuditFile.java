package com.lw.vcs.audit.model;

import java.io.File;

/**
 * @Author：lian.wei
 * @Date：2018/9/23 16:04
 * @Description：审核文件实体类
 */
public class AuditFile {
    private String fileId;
    private String fileName;
    private String filePath;
    private File file;
    private String fileSvnUrl;
    private String svnUserId;
    private String comment;
    private String auditUserId;

    public String getSvnUserId() {
        return svnUserId;
    }

    public AuditFile setSvnUserId(String svnUserId) {
        this.svnUserId = svnUserId;
        return this;
    }

    public static AuditFile create(){
        return new AuditFile();
    }
    public String getFileId() {
        return fileId;
    }

    public AuditFile setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public AuditFile setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getSvnfileSvnUrl() {
        return fileSvnUrl;
    }

    public AuditFile setSvnfileSvnUrl(String svnfileSvnUrl) {
        this.fileSvnUrl = svnfileSvnUrl;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public File getFile() {
        return file;
    }

    public AuditFile setFile(File file) {
        this.file = file;
        return this;
    }

    public AuditFile setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getFileSvnUrl() {
        return fileSvnUrl;
    }

    public AuditFile setFileSvnUrl(String fileSvnUrl) {
        this.fileSvnUrl = fileSvnUrl;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public AuditFile setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public AuditFile setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
        return this;
    }
}
