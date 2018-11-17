package com.lw.vcs.config;

import com.lw.vcs.auth.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/8/8 22:27
 * @Description：
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Autowired
    private UserArgumentResolver userArgumentResolver;

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        List<String> excludeList = new ArrayList<>();
        excludeList.add("/swagger-ui**");
        excludeList.add("/webjars/**");
        excludeList.add("/**/*.js");
        excludeList.add("/**/*.html");
        excludeList.add("/**/*.css");
        excludeList.add("/**/*.png");
        excludeList.add("/**/*.jpg");
        excludeList.add("/swagger-resources/**");
        excludeList.add("/v2/api-docs/**");

        registry.addInterceptor(authorizationInterceptor).excludePathPatterns(excludeList);
//        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
}
