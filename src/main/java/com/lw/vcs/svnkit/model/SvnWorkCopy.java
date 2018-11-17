package com.lw.vcs.svnkit.model;

/**
 * @Author：lian.wei
 * @Date：2018/9/23 23:29
 * @Description：
 */
public class SvnWorkCopy {
    private String workCopyId;
    private String svnId;
    private String workCopyName;
    private String svnUrl;

    public static SvnWorkCopy create(){
        return new SvnWorkCopy();
    }

    public String getWorkCopyId() {
        return workCopyId;
    }

    public SvnWorkCopy setWorkCopyId(String workCopyId) {
        this.workCopyId = workCopyId;
        return this;
    }

    public String getSvnId() {
        return svnId;
    }

    public SvnWorkCopy setSvnId(String svnId) {
        this.svnId = svnId;
        return this;
    }

    public String getWorkCopyName() {
        return workCopyName;
    }

    public SvnWorkCopy setWorkCopyName(String workCopyName) {
        this.workCopyName = workCopyName;
        return this;
    }

    public String getSvnUrl() {
        return svnUrl;
    }

    public SvnWorkCopy setSvnUrl(String svnUrl) {
        this.svnUrl = svnUrl;
        return this;
    }
}
