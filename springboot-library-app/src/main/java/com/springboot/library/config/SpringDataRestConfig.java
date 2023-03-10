package com.springboot.library.config;

import com.springboot.library.entity.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class SpringDataRestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
                                                     CorsRegistry cors) {
        HttpMethod[] unsupportedActions = {
                HttpMethod.POST, HttpMethod.PUT,
                HttpMethod.PATCH, HttpMethod.DELETE
        };

        config.exposeIdsFor(Book.class);

        config.getExposureConfiguration()
                .forDomainType(Book.class)
                .withItemExposure((metdata, httpMethods) ->
                        httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metdata, httpMethods) ->
                        httpMethods.disable(unsupportedActions));

        //Cors Mapping
        String allowedOrigins = "http://localhost:3000"; //to allow frontend to call rest api
        cors.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(allowedOrigins);
    }
}
