package com.url.shortner.url.shortner.service.persistence;

import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author Archetect
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {
                "com.url.shortner.url.shortner.service.persistence.repositories",
        },
        entityManagerFactoryRef = "urlShortnerEMF",
        transactionManagerRef = "urlShortnerTM")
@ComponentScan
public class UrlShortnerServicePersistenceConfig {
    private static final Logger logger = LoggerFactory.getLogger(UrlShortnerServicePersistenceConfig.class );
    private final Environment env;

    @Autowired
    public UrlShortnerServicePersistenceConfig(final Environment env) {
        this.env = env;
    }

    @Bean(name = "urlShortnerTM")
    @Qualifier("urlShortner")
    public JpaTransactionManager urlShortnerTM(
    @Qualifier("urlShortnerDS") final DataSource dataSource,
    @Qualifier("urlShortnerEMF") final EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean urlShortnerEMF(
    @Qualifier("urlShortnerDS") final DataSource dataSource,
    @Qualifier("urlShortnerVA") final JpaVendorAdapter vendorAdapter) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPersistenceUnitName("urlShortner");
        factory.setPackagesToScan(
            "com.url.shortner.url.shortner.service.persistence.entities"
        );
        return factory;
    }

    @Bean
    @Qualifier("urlShortner")
    public JdbcTemplate urlShortnerJdbcTemplate(@Qualifier("urlShortnerDS") final DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    @ConditionalOnProperty(name = "liquibase", havingValue = "true", matchIfMissing = true)
    public SpringLiquibase urlShortnerLiquibase(@Qualifier("urlShortnerDS") final DataSource dataSource) {
        logger.info("Applying Liquibase");
        SpringLiquibase liquibase = new SpringLiquibase();
//         liquibase.setContexts(RuntimeMode.current().name());
        liquibase.setDataSource(dataSource);
        if (env.containsProperty("initdb") || env.containsProperty("gateway.initdb")) {
            liquibase.setDropFirst(true);
        }
        liquibase.setChangeLog("classpath:db/url-shortner-service/changelog-master.xml");
        return liquibase;
    }
}