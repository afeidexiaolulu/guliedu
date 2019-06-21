package com.atguigu.gulixueyuan.edu.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())// 调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .select()//创建ApiSelectorBuilder对象
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))//过滤掉admin接口
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//过滤掉错误路径
                .build();
        //.useDefaultResponseMessages(false);//不使用默认的返回值
    }
    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("web-用户中心微服务API")//大标题
                .description("此文档描述了用户中心web端微服务API")//详细描述
                .version("1.0")//版本
                .contact(new Contact("Helen", "http://atguigu.com", "55317332@qq.com"))//作者
                .build();
    }

    @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())// 调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .select()//创建ApiSelectorBuilder对象
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))//过滤的接口
                .build();
    }

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("后台管理-用户中心微服务API")//大标题
                .description("此文档描述了用户中心后台管理微服务API")//详细描述
                .version("1.0")//版本
                .contact(new Contact("Helen", "http://atguigu.com", "55317332@qq.com"))//作者
                .build();
    }

}
