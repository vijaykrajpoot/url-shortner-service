package com.url.shortner.url.shortner.service.server;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class UrlShortnerServiceServer {

    static {
        if (System.getProperty("logging.config") == null) {
            System.setProperty("logging.config", "classpath:url-shortner-service-server-logback.xml");
        }
    }

    private static final String SPRING_CONFIG_NAME = "spring.config.name";
    private static final String SPRING_APPLICATION_NAME = "spring.application.name";
    private static final String SPRING_JMX_DEFAULT = "spring.jmx.default";
    private static final String SPRING_JMX_ENABLED = "spring.jmx.enabled";
    private static final String APPLICATION_NAME = "url-shortner-service-server";

    private final SpringApplication springApplication;
    private final Properties overrides = new Properties();
    private ConfigurableApplicationContext context;
    private final List<String> arguments = new ArrayList<>();
    private boolean disableJmx = false;

    public UrlShortnerServiceServer() {
        this.springApplication = new SpringApplication(UrlShortnerServiceServerConfig.class);
        springApplication.addInitializers(applicationContext -> applicationContext
                .getEnvironment()
                .getPropertySources()
                .addFirst(new PropertiesPropertySource("overrides", overrides)));
    }

    public UrlShortnerServiceServer start() {
        initializeSystemProperties();
        String[] args = new String[arguments.size()];
        args = arguments.toArray(args);
        this.context = springApplication.run(args);
        clearSystemProperties();
        return this;
    }

    public UrlShortnerServiceServer stop() {
        context.close();
        return this;
    }

    private void initializeSystemProperties() {
        System.setProperty(SPRING_CONFIG_NAME, APPLICATION_NAME);
        System.setProperty(SPRING_APPLICATION_NAME, APPLICATION_NAME);
        System.setProperty(SPRING_JMX_DEFAULT, APPLICATION_NAME);
        if (disableJmx) {
            System.setProperty(SPRING_JMX_ENABLED, "false");
        }
    }

    private void clearSystemProperties() {
        System.clearProperty(SPRING_CONFIG_NAME);
        System.clearProperty(SPRING_APPLICATION_NAME);
        System.clearProperty(SPRING_JMX_DEFAULT);
        if (disableJmx) {
            System.clearProperty(SPRING_JMX_ENABLED);
        }
    }

    public UrlShortnerServiceServer disableJmx() {
        this.disableJmx = true;
        return this;
    }

    public UrlShortnerServiceServer withArguments(String... args) {
        arguments.addAll(Arrays.asList(args));
        return this;
    }

    public UrlShortnerServiceServer withProperty(String key, String value) {
        this.overrides.setProperty(key, value);
        return this;
    }

    public UrlShortnerServiceServer withRandomPort() {
        return withProperty("server.port", "0");
    }

    public Optional<ApplicationContext> getContext() {
        return Optional.ofNullable(context);
    }

    public static void main(String[] args) {
        new UrlShortnerServiceServer().withArguments(args).start();
    }

    public int getServerPort() {
        Integer serverPort = context.getEnvironment().getProperty("local.server.port", Integer.class);
        if (serverPort == null) {
            throw new RuntimeException("Server not started!");
        }
        return serverPort;
    }
}
