package com.example.demo.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket produceApi() {
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(new ApiInfoBuilder()
                .title("NEOM PAY Template REST API")
                .description("NEOM PAY Template REST Service")
                .version("1.0")
                .build())
            .tags(new Tag(ServiceTags.HELLO_WORLD_SERVICE, "Hello world service"))
            .select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .build();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ServiceTags {

        public static final String HELLO_WORLD_SERVICE = "Hello World Service";
    }

}