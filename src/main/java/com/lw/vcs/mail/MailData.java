package com.lw.vcs.mail;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/10/10 21:35
 * @Description：
 */
public class MailData {
    private String from;
    private String subject;
    private String content;
    private List<String> toList = new ArrayList<>();
    private List<String> ccList = new ArrayList<>();
    private List<String> bccList = new ArrayList<>();
    private List<File> attachments = new ArrayList<>();

    public static MailData create() {
        return new MailData();
    }

    public String getFrom() {
        return from;
    }

    public MailData setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MailData setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MailData setContent(String content) {
        this.content = content;
        return this;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public MailData setAttachments(List<File> attachments) {
        this.attachments = attachments;
        return this;
    }
    public MailData addAttachment(File attachment) {
        this.attachments.add(attachment);
        return this;
    }

    public List<String> getToList() {
        return toList;
    }

    public MailData setToList(List<String> toList) {
        this.toList = toList;
        return this;
    }
    public MailData addToList(String to) {
        this.toList.add(to);
        return this;
    }

    public List<String> getCcList() {
        return ccList;
    }

    public MailData setCcList(List<String> ccList) {
        this.ccList = ccList;
        return this;
    }

    public MailData addCcList(String cc) {
        this.ccList.add(cc);
        return this;
    }

    public List<String> getBccList() {
        return bccList;
    }

    public MailData setBccList(List<String> bccList) {
        this.bccList = bccList;
        return this;
    }

    public MailData addBccList(String bcc) {
        this.bccList.add(bcc);
        return this;
    }

    public boolean validate() {
        if (StringUtils.isBlank(this.subject)) {
            return false;
        }else {

        }
        return true;
    }
}
