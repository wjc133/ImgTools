package com.elite.tools.imgtools.utils;

import java.util.Map;

/**
 * Created by wjc133.
 * Date: 2016/1/17
 * Time: 2:01
 * Description:
 */
public class HttpHeaderParser {
    public static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";

    public static String parseCharset(Map<String, String> headers, String defaultCharset) {
        String contentType = headers.get("Content-Type");
        if (contentType != null) {
            String[] params = contentType.split(";");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }

        return defaultCharset;
    }

    public static String parseCharset(Map<String, String> headers) {
        return parseCharset(headers, DEFAULT_CONTENT_TYPE);
    }

}
