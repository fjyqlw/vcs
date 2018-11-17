package com.lw.vcs.svnkit.model;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 17:10
 * @Description：
 */
public class SvnUserCfg {
    private String userId;
    private String svnUserId;
    private String svnPassword;
    private String svnId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSvnUserId() {
        return svnUserId;
    }

    public void setSvnUserId(String svnUserId) {
        this.svnUserId = svnUserId;
    }

    public String getSvnId() {
        return svnId;
    }

    public void setSvnId(String svnId) {
        this.svnId = svnId;
    }

    public String getSvnPassword() {
        return svnPassword;
    }

    public void setSvnPassword(String svnPassword) {
        this.svnPassword = svnPassword;
    }
}
