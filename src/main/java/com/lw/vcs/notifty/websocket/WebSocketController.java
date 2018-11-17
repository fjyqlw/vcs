package com.lw.vcs.notifty.websocket;

/**
 * @Author：lian.wei
 * @Date：2018/10/14 22:00
 * @Description：
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * websocket
 * 消息推送(个人和广播)
 */
@RestController
@RequestMapping("/webSocket")
public class WebSocketController {

    /**
     * 个人信息推送
     *
     * @return
     */
    @RequestMapping("/sendToSingle")
    public String sendToSingle(String msg, String userId) {
        WebSocketServer.sendMessage(msg, userId);
        return "success";
    }

    /**
     * 多人信息推送
     *
     * @return
     */
    @RequestMapping("/sendToMany")
    public String sendToMany(String msg, String userIds) {
        String[] persons = userIds.split(",");
        WebSocketServer.SendMany(msg, persons);
        return "success";
    }

    /**
     * 推送给所有在线用户
     *
     * @return
     */
    @RequestMapping("/sendToAll")
    public String sendToAll(String msg) {
        WebSocketServer.sendAll(msg);
        return "success";
    }

    /**
     * 获取当前在线用户
     *
     * @return
     */
    @RequestMapping("/webStatus")
    public String webStatus() {
        //当前用户个数
        int count = WebSocketServer.getOnlineNum();
        //当前在线所有用户
        String names = WebSocketServer.getOnlineUsers();
        return count + names;
    }
}
