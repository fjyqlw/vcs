package com.lw.vcs.notifty;

import com.alibaba.fastjson.JSON;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @Author：lian.wei
 * @Date：2018/10/14 23:10
 * @Description：
 */
public class ServerEncoder implements Encoder.Text<WebSocketMessage> {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(EndpointConfig arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public String encode(WebSocketMessage messagepojo) throws EncodeException {
        return JSON.toJSONString(messagepojo);
    }

}
