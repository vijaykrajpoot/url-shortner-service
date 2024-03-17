package com.url.shortner.url.shortner.service.server.controllers;

import com.url.shortner.url.shortner.service.api.UrlApi;
import com.url.shortner.url.shortner.service.api.config.api.model.URL;
import com.url.shortner.url.shortner.service.core.UrlShorterServiceCoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/url")
public class URLShortController implements UrlApi {

    @RequestMapping("/")
    public String root() {
        return "Url Shortner Service";
    }

    UrlShorterServiceCoreService urlShorterServiceCoreService;

    public URLShortController(UrlShorterServiceCoreService urlShorterServiceCoreService) {
        this.urlShorterServiceCoreService = urlShorterServiceCoreService;
    }

    @Override
    @PostMapping("/shortUrl")
    public ResponseEntity<URL> shortUrl(URL url) {
        URI uri = null;
        System.out.println("url:" + url.getUrl().toString());

       String shortUrl= urlShorterServiceCoreService.shortUrl(url.getUrl().toString());

        try {
            uri = new URI(shortUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        URL shortURL = new URL();
        shortURL.setUrl(uri);
        return new ResponseEntity<>(shortURL, HttpStatus.OK);
    }
}
