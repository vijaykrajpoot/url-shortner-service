package com.url.shortner.url.shortner.service.server.controllers;

import com.url.shortner.url.shortner.service.api.UrlApi;
import com.url.shortner.url.shortner.service.api.config.api.model.URL;
import com.url.shortner.url.shortner.service.core.UrlShorterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/url")
public class URLShortController implements UrlApi {
    private static final Logger logger = LoggerFactory.getLogger(URLShortController.class);

    @RequestMapping("/")
    public String root() {
        return "Url Shortner Service";
    }

    UrlShorterServiceImpl urlShorterServiceImpl;

    public URLShortController(UrlShorterServiceImpl urlShorterServiceImpl) {
        this.urlShorterServiceImpl = urlShorterServiceImpl;
    }

    @Override
    @PostMapping("/shortUrl")
    public ResponseEntity<URL> shortUrl(URL url) {
        URI uri = null;
        logger.info("incoming long url: {}", url.getUrl().toString());
        String shortUrl = urlShorterServiceImpl.shortUrl(url.getUrl().toString());
        try {
            uri = new URI(shortUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        URL shortURL = new URL();
        shortURL.setUrl(uri);
        logger.info(".shortUrl() Sort URL :{}, Long URL:{}", url.getUrl().toString(), shortURL.getUrl().toString());
        return new ResponseEntity<>(shortURL, HttpStatus.OK);
    }


    //    @Override
    @GetMapping(value = "/longUrl")
    public ResponseEntity<Void> longUrl(String shortUrl) {
        String longURL = urlShorterServiceImpl.findLongUrl(shortUrl);
        logger.debug("Found Long URL:{}", longURL);
        if (longURL != null) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(URI.create(longURL)); // Set location header with the long URL
            logger.info(".longUrl() Sort URL :{}, Long URL:{}", shortUrl, longURL);
            return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }
}
