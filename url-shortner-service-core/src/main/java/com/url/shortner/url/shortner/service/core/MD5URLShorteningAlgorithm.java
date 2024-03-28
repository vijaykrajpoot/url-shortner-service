package com.url.shortner.url.shortner.service.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class MD5URLShorteningAlgorithm implements URLShorteningAlgorithm {
    private static final Logger logger = LoggerFactory.getLogger(MD5URLShorteningAlgorithm.class);
    private final MessageDigest messageDigest;

    public MD5URLShorteningAlgorithm() {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing MD5 MessageDigest", e);
        }
    }

    @Override
    public String shorten(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Inout can't be null");
        }
        StringBuilder binaryStr = new StringBuilder();
        // Add nano second to ensure has is always unique
        input = input + System.nanoTime();
        byte[] byteArr = messageDigest.digest(input.getBytes());
        logger.debug("byteArr: {}", Arrays.toString(byteArr));
        // Convert byte array to bigInt by passing signum as 1 to eusure +ve big Integer
        BigInteger bigInteger = new BigInteger(1, byteArr);
        logger.debug("bigInteger :{}", bigInteger);
        String bString = bigInteger.toString(2);
        logger.debug("Binary String from BingInteger:{} With length:{}", bString, bString.length());
        // append bString
        binaryStr.append(bString);
        // in case of length is less than 128 then pad the string with leading zero
        while (binaryStr.length() < 128) {
            binaryStr.insert(0, "0");
        }
        return binaryStr.toString();
    }
}
