package com.lw.vcs.log;

/**
 * @Author：lian.wei
 * @Date：2018/9/28 20:37
 * @Description：
 */
public enum LogType {
    LOGIN("LOGIN"),LOGOUT("LOGOUT");

    private final String value;

    private LogType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
