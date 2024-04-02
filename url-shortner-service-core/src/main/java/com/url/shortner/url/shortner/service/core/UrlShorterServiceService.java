package com.url.shortner.url.shortner.service.core;


import com.url.shortner.url.shortner.service.persistence.entities.URLEntity;
import com.url.shortner.url.shortner.service.persistence.repositories.URLHashMapRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlShorterServiceService implements UrlShorterService {
    private static final Logger logger = LoggerFactory.getLogger(UrlShorterServiceService.class);
    private final URLShorteningAlgorithm urlShorteningAlgorithm;


    private final URLHashMapRepository urlHashMapRepository;
    private static final int BITS_SEGMENT = 6;

    private static final int urlLength = 6;

    public UrlShorterServiceService(URLShorteningAlgorithm urlShorteningAlgorithm, URLHashMapRepository urlHashMapRepository) {
        this.urlShorteningAlgorithm = urlShorteningAlgorithm;
        this.urlHashMapRepository = urlHashMapRepository;
    }


    @Override
    public String shortUrl(String longUrl) {
        logger.info("longUrl :{}", longUrl);
        String binaryString = urlShorteningAlgorithm.binaryString(longUrl);
        // Need this multiplication because 6 bits each time
        // so if we need X length URL it must be (X*Six Bit) and should not go beyond 128 long

        int bitSegment = urlLength * BITS_SEGMENT;
        if (bitSegment > 128) {
            throw new IllegalArgumentException("Please check shortner URL length. It should not be > 21 ");
        }
        StringBuilder shortURL = new StringBuilder();
        for (int i = 0; i < bitSegment; i += BITS_SEGMENT) {
            logger.debug("Index Value:{}", i);
            String sixBits = binaryString.substring(i, Math.min(i + BITS_SEGMENT, binaryString.length()));
            int decimalValue = Integer.parseInt(sixBits, 2);
            logger.debug("sixBits:" + sixBits + " Decimal Value:" + decimalValue);
            shortURL.append(getBase64Character(decimalValue));
        }
        String finalShortURL = shortURL.toString();
        logger.info("Final Short URL Value :" + finalShortURL);
        finalShortURL = "https://mydomain.com/" + finalShortURL;
        URLEntity url = new URLEntity();
        url.setSortURL(finalShortURL);
        url.setLongURL(longUrl);
        urlHashMapRepository.saveURL(url);
        return finalShortURL;
    }


    @Override
    public String findLongUrl(String sortUrl) {
        logger.debug("sortURL:" + sortUrl);
        return urlHashMapRepository.getLongURL(sortUrl);
    }

    public static char getBase64Character(int decimalValue) {
        // Base64 character array
        String base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        // Map decimal value to Base64 character
        return base64Chars.charAt(decimalValue);
    }


}
