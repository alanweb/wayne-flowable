package com.wayne.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Autowired
    /*ServletContext,是一个全局的储存信息的空间，服务器开始，其就存在，服务器关闭，
    其才释放。request，一个用户可有多个；session，一个用户一个；而servletContext，
    所有用户共用一个。所以，为了节省空间，提高效率，ServletContext中，
    要放必须的、重要的、所有用户需要共享的线程又是安全的一些信息。*/
    private ServletContext servletContext;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("flowable接口")
                .enable(true)
                .select()
                //接口的包名
                .apis(RequestHandlerSelectors.basePackage("com.wayne.controller"))
                .paths(PathSelectors.any())
                .build()
                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return "/";
                    }
                });
    }
}