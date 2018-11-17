/**
 * Copyright (C) 2012-2018 ZEEI Inc.All Rights Reserved.
 * 项目名称：mail
 * 文件名称：MailUtil.java
 * 包  名  称：com.lw.mail
 * 文件描述：TODO 请修改文件描述
 * 创建日期：2018年10月10日下午2:32:47
 * <p>
 * 修改历史
 * 1.0 lian.wei 2018年10月10日下午2:32:47 创建文件
 */

package com.lw.vcs.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;

/**
 * @类型名称：MailUtil
 * @类型描述：TODO 请修改文件描述
 * @功能描述：TODO 请修改功能描述
 * @创建作者：lian.wei
 */
public class MailUtil {

    private static Logger logger = LoggerFactory.getLogger(MailUtil.class);


    public static void sendMail(MailData mailData, MailSession session) {

        // 创建邮件消息
        MimeMessage message = new MimeMessage(session.getSession());

        try {
            // 设置发件人
            InternetAddress form = new InternetAddress(mailData.getFrom());
            message.setFrom(form);

            // 设置收件人
            InternetAddress[] to = new InternetAddress[mailData.getToList().size()];
            for (int i = 0; i < mailData.getToList().size(); i++) {
                to[i] = new InternetAddress(mailData.getToList().get(i));
            }
            message.setRecipients(RecipientType.TO, to);

            // 设置抄送
            InternetAddress[] cc = new InternetAddress[mailData.getCcList().size()];
            for (int i = 0; i < mailData.getCcList().size(); i++) {
                cc[i] = new InternetAddress(mailData.getCcList().get(i));
            }
            message.setRecipients(RecipientType.CC, cc);

            // 设置密送，其他的收件人不能看到密送的邮件地址
            InternetAddress[] bcc = new InternetAddress[mailData.getBccList().size()];
            for (int i = 0; i < mailData.getBccList().size(); i++) {
                bcc[i] = new InternetAddress(mailData.getBccList().get(i));
            }
            message.setRecipients(RecipientType.BCC, bcc);

            // 设置邮件标题
            message.setSubject(mailData.getSubject());

            // 邮件正文
            Multipart multipart = new MimeMultipart();
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(mailData.getContent(), "text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);

            // 邮件附件
            if (mailData.getAttachments() != null) {
                for (File attachment : mailData.getAttachments()) {
                    BodyPart attachmentPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachment);
                    attachmentPart.setDataHandler(new DataHandler(source));
                    // 避免中文乱码的处理
                    attachmentPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
                    multipart.addBodyPart(attachmentPart);
                }
            }
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            Transport.send(message);

            logger.info(String.format("邮件：%s 发送成功！", mailData.getSubject()));

        } catch (Exception e) {
            logger.error(String.format("邮件：%s 发送失败！", mailData.getSubject()));
            logger.error(e.toString());
        }
    }
}
