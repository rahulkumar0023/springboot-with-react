package com.luv2code.springbootlibrary.config;

import com.luv2code.springbootlibrary.entity.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private String allowedOrigins = "http://localhost:3000";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions =
                {
                        HttpMethod.POST,
                        HttpMethod.PATCH,
                        HttpMethod.DELETE,
                        HttpMethod.PUT
                };
        config.exposeIdsFor(Book.class);
        disableHttpMethods(Book.class, config, theUnsupportedActions);
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(allowedOrigins);
    }

    private void disableHttpMethods(Class<Book> bookClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(bookClass)
                .withItemExposure((metdata, httpMethods) ->
                        httpMethods.disable())
                .withCollectionExposure((metdata, httpMethods) ->
                        httpMethods.disable());
    }
}
