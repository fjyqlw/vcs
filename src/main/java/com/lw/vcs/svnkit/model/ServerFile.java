package com.lw.vcs.svnkit.model;

/**
 * @Author：lian.wei
 * @Date：2018/11/28 20:58
 * @Description：文件
 */
public class ServerFile {
    private String fileId;
    private String fileName;
    private String serverFileName;
    private Long fileSize;
    private String uploadTime;

    public static ServerFile create() {
        return new ServerFile();
    }

    public String getFileId() {
        return fileId;
    }

    public ServerFile setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public ServerFile setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public ServerFile setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
        return this;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public ServerFile setFileSize(Long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public ServerFile setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
        return this;
    }
}
