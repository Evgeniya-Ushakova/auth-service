package com.evg.authservice.utils;

import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;

public class Utils {

    public static String getRequestBodyAsString(ContentCachingRequestWrapper requestWrapper) {
        return new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
    }

}
