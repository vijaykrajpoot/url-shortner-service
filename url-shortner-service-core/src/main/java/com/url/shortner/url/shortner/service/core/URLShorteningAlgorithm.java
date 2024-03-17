package com.url.shortner.url.shortner.service.core;

import org.springframework.stereotype.Service;


public interface URLShorteningAlgorithm {
    String shorten(String input);
}
