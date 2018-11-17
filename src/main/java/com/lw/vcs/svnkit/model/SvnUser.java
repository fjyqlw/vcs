package com.lw.vcs.svnkit.model;

import java.io.File;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 16:04
 * @Description： SVN用户对象实体类
 */
public class SvnUser {
    private String userName;
    private String password;
    private String svnUrl;
    /**
     * svn工作目录
     */
    private File wcPath;
    private File dstPath;

    public static SvnUser create(){
        return new SvnUser();
    }
    public String getUserName() {
        return userName;
    }

    public SvnUser setUserName(String userName) {
        this.userName = userName;

        return this;
    }

    public String getPassword() {
        return password;
    }

    public SvnUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSvnUrl() {
        return svnUrl;
    }

    public SvnUser setSvnUrl(String svnUrl) {
        this.svnUrl = svnUrl;
        return this;
    }

    public File getWcPath() {
        return wcPath;
    }

    public SvnUser setWcPath(File wcPath) {
        this.wcPath = wcPath;
        return this;
    }

    public File getDstPath() {
        return dstPath;
    }

    public SvnUser setDstPath(File dstPath) {
        this.dstPath = dstPath;
        return this;
    }
}
