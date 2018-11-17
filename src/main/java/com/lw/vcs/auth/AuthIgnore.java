package com.lw.vcs.auth;

import java.lang.annotation.*;

/**
 * @Author：lian.wei
 * @Date：2018/9/28 21:04
 * @Description：
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore {
}
