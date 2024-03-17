package com.url.shortner.url.shortner.service.core;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class MD5URLShorteningAlgorithm implements URLShorteningAlgorithm {
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
        if (input != null) {
            input = input + System.nanoTime();
        }
        byte[] byteArr = messageDigest.digest(input.getBytes());
        BigInteger bigInteger = new BigInteger(1, byteArr);
        String bString = bigInteger.toString(2);
        StringBuilder binaryStr = new StringBuilder(bString);
        while (binaryStr.length() < 128) {
            binaryStr.insert(0, "0"); // Pad with leading zeros if necessary
        }
        return binaryStr.toString();
    }
}
