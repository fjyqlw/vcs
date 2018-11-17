package com.lw.vcs.svnkit.model;

/**
 * @Author：lian.wei
 * @Date：2018/9/24 21:20
 * @Description：svn资源库
 */
public class SvnRepository {
    private String svnId;
    private String svnName;
    private String svnUrl;
    private String createTime;
    private int state;

    public static SvnRepository create(){
        return new SvnRepository();
    }
    public String getSvnId() {
        return svnId;
    }

    public SvnRepository setSvnId(String svnId) {
        this.svnId = svnId;
        return this;
    }

    public String getSvnName() {
        return svnName;
    }

    public SvnRepository setSvnName(String svnName) {
        this.svnName = svnName;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public SvnRepository setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getSvnUrl() {
        return svnUrl;
    }

    public SvnRepository setSvnUrl(String svnUrl) {
        this.svnUrl = svnUrl;
        return this;
    }

    public int getState() {
        return state;
    }

    public SvnRepository setState(int state) {
        this.state = state;
        return this;
    }
}
