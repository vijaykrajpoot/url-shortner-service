package com.url.shortner.url.shortner.service.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Payment Gateway Archetype
 */

@EnableSwagger2
@Configuration
@ComponentScan(basePackages = "com.url.shortner.url.shortner.service.api")
public class UrlShortnerServiceSwaggerConfig {
    /**
     * Load the swagger definition
     *
     * @return SwaggerResourcesProvider swagger resource definition
     */
    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            SwaggerResource wsResource = new SwaggerResource();
            wsResource.setName("url-shortner-service");
            wsResource.setSwaggerVersion("2.0");
            wsResource.setLocation("/openapi/url-shortner-service.yaml");
            List<SwaggerResource> resources = new ArrayList<>();
            resources.add(wsResource);
            return resources;
        };
    }

}
