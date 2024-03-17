package com.url.shortner.url.shortner.service.server;

import com.url.shortner.url.shortner.service.core.UrlShortnerServiceCoreConfig;
import com.url.shortner.url.shortner.service.api.config.UrlShortnerServiceSwaggerConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        UrlShortnerServiceSwaggerConfig.class,
        UrlShortnerServiceCoreConfig.class,
})
public class UrlShortnerServiceServerConfig {

}
