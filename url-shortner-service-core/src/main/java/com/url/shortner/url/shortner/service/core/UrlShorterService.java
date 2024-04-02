package com.url.shortner.url.shortner.service.core;

public interface UrlShorterService {

    String shortUrl(String longUrl);

    String findLongUrl(String longUrl);
}
