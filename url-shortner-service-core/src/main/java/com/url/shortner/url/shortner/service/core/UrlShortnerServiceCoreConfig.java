package com.url.shortner.url.shortner.service.core;

import com.url.shortner.url.shortner.service.persistence.UrlShortnerServicePersistenceConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.Set;

@Configuration
@Import({
        UrlShortnerServicePersistenceConfig.class,
})
@ComponentScan
public class UrlShortnerServiceCoreConfig {

    @Bean
    @Qualifier("urlShortner")
    public ConversionService urlShortnerConversionService(Set<Converter<?,?>> converters) {
        ConfigurableConversionService conversionService = new DefaultConversionService();
        converters.forEach(conversionService::addConverter);
        return conversionService;
    }
}