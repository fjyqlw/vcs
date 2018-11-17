package com.lw.vcs.mail;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author：lian.wei
 * @Date：2018/10/10 21:29
 * @Description：
 */
public class MailUtilTest {

    @Test
    public void sendMail() {
        MailData mailData = MailData.create();
        mailData.setSubject("测试邮件")
                .setFrom("fjyqlw@foxmail.com")
                .setContent("<p>Mysql 中字符串转时间跟<span style='color:red;'>Oracle略不同</span>，函数为 str_to_date <br>\n" +
                        "应注意的是里面的大小写<br/><hr/> 如下： <br>\n" +
                        "MySQL内置函数，在mysql里面利用str_to_date（）把字符串转换为日期。 <br>\n" +
                        "示例：分隔符一致，年月日要一致</p>")
                .addToList("1126028813@qq.com")
                .addCcList("fjyqlw@foxmail.com")
                .addCcList("1126028813@qq.com")
                .addBccList("fjyqlw@foxmail.com");
//        new MailUtil().sendMail(mailData);
    }
}