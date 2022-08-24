package com.lunar.lunarMgmt.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Set;

@Configuration
public class SwaggerConfig {

    @Value("${system.swagger.host}")
    private String host;

    @Value("${system.swagger.protocols}")
    private Set<String> protocols;

    @Bean
    public Docket api() {// swagger 설정의 핵심이 되는 bean
        return new Docket(DocumentationType.OAS_30)// open api spec 3.0
                .useDefaultResponseMessages(false)// Swagger 에서 제공해주는 기본 응답 코드 (200, 401, 403, 404). false 로 설정하면 기본 응답 코드를 노출하지 않음
                .select()
                .apis(RequestHandlerSelectors.any())// 모든 url
                .paths(PathSelectors.any())// 모든 path
                .build()
                .host(host)
                .protocols(protocols)
                .apiInfo(apiInfo());// swagger UI로 노출할 정보
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Practice Swagger")
                .description("practice swagger config")
                .version("1.0")
                .build();
    }
}
