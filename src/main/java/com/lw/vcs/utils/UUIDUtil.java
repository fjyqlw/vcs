package com.lw.vcs.utils;

import java.util.UUID;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 15:48
 * @Description：
 */
public class UUIDUtil {

    public static String getUUID(){
        return  UUID.randomUUID().toString().replace("-","");
    }
}
