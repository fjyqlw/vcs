package com.lw.vcs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @Author：lian.wei
 * @Date：2018/6/30 20:02
 * @Description：
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 可以定义多个组，比如本类中定义把test和demo区分开了
     * （访问页面就可以看到效果了）
     *
     */
    @Bean
    public Docket apiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("v1.0")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lw.vcs"))
                .paths(PathSelectors.any())//过滤的接口
                .build()
                .apiInfo(apiInfoV1());
    }

    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("版本控制管理系统API")//大标题
                .description("SVN权限控制")//详细描述
                .version("1.0")//版本
                .contact(new Contact("lian.wei", "", "1126028813@qq.com"))//作者
                .build();
    }

}
