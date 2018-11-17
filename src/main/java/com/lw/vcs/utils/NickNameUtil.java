package com.lw.vcs.utils;

/**
 * @Author：lian.wei
 * @Date：2018/9/27 20:19
 * @Description：
 */
public class NickNameUtil {

    public String defaultNickName(String name) {
        int length = name.length();

        if(length <= 2){
            return name;
        } else {
            return name.substring(length-2,length);
        }
    }
}
