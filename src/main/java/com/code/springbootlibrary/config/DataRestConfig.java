package com.code.springbootlibrary.config;

import com.code.springbootlibrary.entity.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    private final String theAllowedOrigins="http://localhost:3000";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.POST, HttpMethod.DELETE};

        // disable HTTP methods for Book: PUT, POST and DELETE
        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Review.class);
        config.exposeIdsFor(Checkout.class);
        config.exposeIdsFor(History.class);
        config.exposeIdsFor(Message.class);
        
        // disable HTTP methods for Book: PUT, POST and DELETE
        disableHttpMethods(Book.class, config, theUnsupportedActions);
        disableHttpMethods(Review.class, config, theUnsupportedActions);
        disableHttpMethods(Checkout.class, config, theUnsupportedActions);
        disableHttpMethods(History.class, config, theUnsupportedActions);
        disableHttpMethods(Message.class, config, theUnsupportedActions);


        // configure CORS mapping
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) ->
                        httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) ->
                        httpMethods.disable(theUnsupportedActions));
    }
}
