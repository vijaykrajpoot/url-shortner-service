package com.url.shortner.url.shortner.service.persistence.repositories;

import com.url.shortner.url.shortner.service.persistence.UrlShortnerServicePersistenceConfig;
import com.url.shortner.url.shortner.service.persistence.entities.URLEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UrlShortnerServicePersistenceConfig.class})
@SpringBootTest(properties = { "db=h2" })
public class TransactionRepositoryTest {

    @Autowired
    private UrlRepository transactionRepository;

    @Test
    void test_create() {
        URLEntity entity = new URLEntity();
        //transactionRepository.save(entity);
    }
}