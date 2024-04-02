package com.url.shortner.url.shortner.service.persistence.repositories;

import com.url.shortner.url.shortner.service.persistence.entities.URLEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

@Repository
public class URLHashMapRepository {
    private static final Logger logger = LoggerFactory.getLogger(URLHashMapRepository.class);
    HashMap<String, String> map = new HashMap<>();

    public void saveURL(URLEntity url) {
        map.put(url.getSortURL(), url.getLongURL());
        logger.debug("Map Data:"+ map);
    }

    public String getLongURL(String sortURL) {
        logger.debug("map:" + map);
        return map.get(sortURL);
    }

}
