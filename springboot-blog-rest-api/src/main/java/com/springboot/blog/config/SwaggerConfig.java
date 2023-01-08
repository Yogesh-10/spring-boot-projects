package com.springboot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    //configure swagger ui
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(List.of(securityContext()))//Enable JWT API key - By default, there will no header and authorization
                .securitySchemes(List.of(apiKey()))//type of key and pass info through header
                .apiInfo(apiInfo()) //Api info - such as title and description
                .select()
                .apis(RequestHandlerSelectors.any()) //generate docs for all apis
                .paths(PathSelectors.any()) //for all paths
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiKey apiKey(){
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Spring Boot REST Blog API",
                "Spring Boot REST Blog API Documentation",
                "v1",
                "Provide Terms of service URL",
                new Contact("Yogesh", "example.com", "email@example.com"),
                "Provide License of API here",
                "Provide URL of API License",
                Collections.emptyList()
        );
    }
}
