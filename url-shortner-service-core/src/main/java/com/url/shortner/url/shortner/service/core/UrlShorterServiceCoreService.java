package com.url.shortner.url.shortner.service.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UrlShorterServiceCoreService implements UrlShortnerService {
    private static final Logger logger = LoggerFactory.getLogger(UrlShorterServiceCoreService.class);
    private final MessageDigest messageDigest;

    public UrlShorterServiceCoreService() throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance("MD5");
    }

    @Override
    public String shortUrl(String longUrl) {
        logger.info("longUrl:" + longUrl);
        String binaryString = getBinaryStringMD5(longUrl);
        int shortenURLLength = 6;
        int bitSegment = shortenURLLength * 6;
        StringBuilder shortURL = new StringBuilder();
        for (int i = 0; i < bitSegment; i += 6) {
            String sixBits = binaryString.substring(i, Math.min(i + 6, binaryString.length()));
            int decimalValue = Integer.parseInt(sixBits, 2);
            logger.debug("sixBits:" + sixBits + " Decimal Value:" + decimalValue);
            shortURL.append(getBase64Character(decimalValue));
        }
        String finalShortURL = shortURL.toString();
        logger.info("Final Short URL Value :" + finalShortURL);
        return "https://mydomain.com/" + finalShortURL;
    }

    private String getBinaryStringMD5(String inputURL) {
        inputURL = inputURL + System.nanoTime();
        byte[] byteArr = messageDigest.digest(inputURL.getBytes());
        BigInteger bigInteger = new BigInteger(1, byteArr);
        String bString = bigInteger.toString(2);
        StringBuilder binaryStr = new StringBuilder();
        binaryStr.append(bString);
        while (binaryStr.length() < 128) {
            binaryStr.insert(0, "0"); // Pad with leading zeros if necessary

        }
        return binaryStr.toString();
    }

    public static char getBase64Character(int decimalValue) {
        // Base64 character array
        String base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        // Map decimal value to Base64 character
        return base64Chars.charAt(decimalValue);
    }


}
