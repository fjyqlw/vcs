package com.lw.vcs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author：lian.wei
 * @Date：2018/10/14 21:57
 * @Description：
 */

@Configuration
public class WebSocketConfig {

    //打成war包部署在tomcat中的话不需要这个bean
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}

