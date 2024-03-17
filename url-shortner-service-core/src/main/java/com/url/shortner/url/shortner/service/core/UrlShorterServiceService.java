package com.url.shortner.url.shortner.service.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UrlShorterServiceService implements UrlShorterService {
    private static final Logger logger = LoggerFactory.getLogger(UrlShorterServiceService.class);
    private final URLShorteningAlgorithm urlShorteningAlgorithm;


    public UrlShorterServiceService(URLShorteningAlgorithm urlShorteningAlgorithm) {
        this.urlShorteningAlgorithm = urlShorteningAlgorithm;
    }

    @Override
    public String shortUrl(String longUrl) {
        logger.info("longUrl :{}", longUrl);
        String binaryString = urlShorteningAlgorithm.shorten(longUrl);
        int shortenURLLength = 6;
        int bitSegment = shortenURLLength * 6;
        StringBuilder shortURL = new StringBuilder();
        for (int i = 0; i < bitSegment; i += 6) {
            String sixBits = binaryString.substring(i, Math.min(i + 6, binaryString.length()));
            int decimalValue = Integer.parseInt(sixBits, 2);
           // logger.debug("sixBits:" + sixBits + " Decimal Value:" + decimalValue);
            shortURL.append(getBase64Character(decimalValue));
        }
        String finalShortURL = shortURL.toString();
        logger.info("Final Short URL Value :" + finalShortURL);
        return "https://mydomain.com/" + finalShortURL;
    }

    public static char getBase64Character(int decimalValue) {
        // Base64 character array
        String base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        // Map decimal value to Base64 character
        return base64Chars.charAt(decimalValue);
    }


}
