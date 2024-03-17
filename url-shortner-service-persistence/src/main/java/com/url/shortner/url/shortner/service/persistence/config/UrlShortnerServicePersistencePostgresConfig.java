package com.url.shortner.url.shortner.service.persistence.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * @author Service Archetype
 */
@Configuration
@ConditionalOnProperty(name = "db", havingValue = "postgres")
public class UrlShortnerServicePersistencePostgresConfig {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortnerServicePersistencePostgresConfig.class);

    private final Environment env;

    @Autowired
    public UrlShortnerServicePersistencePostgresConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource urlShortnerDS() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPoolName("urlShortner");
        logger.info("Configuring UrlShortner Persistence with a PostgreSQL database");
        dataSource.setJdbcUrl(env.getRequiredProperty("url-shortner-service.db.postgres.url"));
        dataSource.setUsername(env.getRequiredProperty("url-shortner-service.db.postgres.user"));
        dataSource.setPassword(env.getRequiredProperty("url-shortner-service.db.postgres.password"));
        if (env.containsProperty("url-shortner-service.db.postgres.pool.maximumPoolSize")) {
            dataSource.setMaximumPoolSize(env.getProperty("url-shortner-service.db.postgres.pool.maximumPoolSize", Integer.class));
        }
        if (env.containsProperty("url-shortner-service.db.postgres.pool.connectionTimeout")) {
            dataSource.setConnectionTimeout(env.getProperty("url-shortner-service.db.postgres.pool.connectionTimeout", Long.class));
        }
        if (env.containsProperty("url-shortner-service.db.postgres.pool.maxLifetime")) {
            dataSource.setMaxLifetime(env.getProperty("url-shortner-service.db.postgres.pool.maxLifetime", Long.class));
        }
        if (env.containsProperty("url-shortner-service.db.postgres.pool.idleTimeout")) {
            dataSource.setIdleTimeout(env.getProperty("url-shortner-service.db.postgres.pool.idleTimeout", Long.class));
        }
        return dataSource;
    }

    @Bean
    public HibernateJpaVendorAdapter urlShortnerVA() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
//         vendorAdapter.setShowSql(Switches.showSql.isEnabled());
        return vendorAdapter;
    }
}
