package com.lw.vcs.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author：lian.wei
 * @Date：2018/9/28 20:03
 * @Description：
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemLog {
    String value();
    LogType type();
    boolean auth() default true;
}
