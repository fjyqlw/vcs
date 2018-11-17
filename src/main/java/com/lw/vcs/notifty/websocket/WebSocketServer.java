package com.lw.vcs.notifty.websocket;

import com.alibaba.fastjson.JSON;
import com.lw.vcs.notifty.ServerEncoder;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：lian.wei
 * @Date：2018/10/14 21:58
 * @Description：
 */


@ServerEndpoint(value = "/socketServer/{userId}")
@Component
public class WebSocketServer {
    private Session session;
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, String> sessionIds = new ConcurrentHashMap<>();

    /**
     * 用户连接时触发
     *
     * @param session
     * @param userId
     */
    @OnOpen
    public void open(Session session, @PathParam(value = "userId") String userId) {
        this.session = session;
        sessionPool.put(userId, session);
        sessionIds.put(session.getId(), userId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        sendMessage(sessionIds.get(session.getId()) + "<--" + message, "99");
    }

    /**
     * 连接关闭触发
     */
    @OnClose
    public void onClose() {
        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());
    }

    /**
     * 发生错误时触发
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 信息发送的方法
     *
     * @param message
     * @param userId
     */
    public static void sendMessage(Object message, String userId) {
        Session session = sessionPool.get(userId);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(JSON.toJSONString(message));
            } catch (IOException e) {
                e.printStackTrace();
//            } catch (EncodeException e) {
//                e.printStackTrace();
            }
        }
    }

    /**
     * 获取当前连接数
     *
     * @return
     */
    public static int getOnlineNum() {
        if (sessionIds.values().contains("99")) {
            return sessionPool.size() - 1;
        }
        return sessionPool.size();
    }

    /**
     * 获取在线用户名以逗号隔开
     *
     * @return
     */
    public static String getOnlineUsers() {
        StringBuffer users = new StringBuffer();
        for (String key : sessionIds.keySet()) {//99是服务端自己的连接，不能算在线人数
            if (!"99".equals(sessionIds.get(key))) {
                users.append(sessionIds.get(key) + ",");
            }
        }
        return users.toString();
    }

    /**
     * 信息群发
     *
     * @param msg
     */
    public static void sendAll(String msg) {
        for (String key : sessionIds.keySet()) {
            if (!"99".equals(sessionIds.get(key))) {
                sendMessage(msg, sessionIds.get(key));
            }
        }
    }

    /**
     * 多个人发送给指定的几个用户
     *
     * @param msg
     * @param userIds 用户s
     */

    public static void SendMany(String msg, String[] userIds) {
        for (String userId : userIds) {
            sendMessage(msg, userId);
        }
    }
}
