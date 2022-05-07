package com.techeer.svproject.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi Api() {
        return GroupedOpenApi.builder()
                .group("API")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI bauctionOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("2Techeer-May-Hackathon")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}