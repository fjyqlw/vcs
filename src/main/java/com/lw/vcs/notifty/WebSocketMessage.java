package com.lw.vcs.notifty;

import java.io.Serializable;

/**
 * @Author：lian.wei
 * @Date：2018/10/14 23:08
 * @Description：
 */
public class WebSocketMessage implements Serializable {

    private static final long serialVersionUID = -6451812593150428369L;

    private String sourse;// 信息来源
    private String messageType;// 消息类型
    private String msgContent;// 消息内容
    private String target;// 发送目的地
    private String infoSourceIP;// 信息来源ip
    private String createtime;// 消息保存时间
    private String otherContent;// 其他信息

    public WebSocketMessage() {
        super();
    }

    public WebSocketMessage(String sourse, String messageType, String msgContent,
                            String target, String infoSourceIP, String createtime,
                            String otherContent) {
        super();
        this.sourse = sourse;
        this.messageType = messageType;
        this.msgContent = msgContent;
        this.target = target;
        this.infoSourceIP = infoSourceIP;
        this.createtime = createtime;
        this.otherContent = otherContent;
    }

    public String getSourse() {
        return sourse;
    }

    public void setSourse(String sourse) {
        this.sourse = sourse;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getInfoSourceIP() {
        return infoSourceIP;
    }

    public void setInfoSourceIP(String infoSourceIP) {
        this.infoSourceIP = infoSourceIP;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getOtherContent() {
        return otherContent;
    }

    public void setOtherContent(String otherContent) {
        this.otherContent = otherContent;
    }

    @Override
    public String toString() {
        return "Messagepojo [sourse=" + sourse + ", messageType=" + messageType
                + ", msgContent=" + msgContent + ", target=" + target
                + ", infoSourceIP=" + infoSourceIP + ", createtime="
                + createtime + ", otherContent=" + otherContent + "]";
    }


}
