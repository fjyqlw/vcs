package com.lw.vcs.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;

/**
 * @Author：lian.wei
 * @Date：2018/8/9 21:59
 * @Description：MD5工具类
 */
public class MD5Util {
    private static final String salt ="Vxci9FyYryROFyT6d55v";
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    /**
     * 明文密码加密，第一次加密
     * @param inputPass
     * @return
     */
    public static String inputPass2FormPass(String inputPass){
        StringBuilder sb = new StringBuilder();
        sb.append(salt.charAt(0));
        sb.append(salt.charAt(2));
        sb.append(salt.charAt(4));
        sb.append(inputPass);
        sb.append(salt.charAt(5));
        sb.append(salt.charAt(7));

        return md5(sb.toString());
    }

    /**
     * 客户端加密后的密码二次加密
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPass2DBPass(String formPass,String salt){
        StringBuilder sb = new StringBuilder();
        sb.append(salt.charAt(0));
        sb.append(salt.charAt(12));
        sb.append(salt.charAt(4));
        sb.append(formPass);
        sb.append(salt.charAt(5));
        sb.append(salt.charAt(17));

        return md5(sb.toString());
    }

    /**
     * 明文密码转加密后的数据库密码
     * @param inputPass
     * @param saltDB
     * @return
     */
    public static String inputPass2DBPass(String inputPass,String saltDB){
        return formPass2DBPass(inputPass2FormPass(inputPass),saltDB);
    }

    public static String getSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[15];
        random.nextBytes(bytes);
        String salt = org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);

        return salt;
    }
    public static void main(String[] args) {
        String pwd0 = "lian.wei";
        //第一次明文加密
        String pwd1 = inputPass2FormPass(pwd0);
        System.out.println(pwd1);
        //生成盐
        String salt = getSalt();
        System.out.println(salt);
        //第二次加密
        String pwd2 = formPass2DBPass(pwd1,salt);
        System.out.println(pwd2);

        System.out.println(getSalt());
        System.out.println(getSalt());
        System.out.println(getSalt());
        System.out.println(getSalt());

    }

}
