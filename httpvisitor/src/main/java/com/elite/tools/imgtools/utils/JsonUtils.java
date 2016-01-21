package com.elite.tools.imgtools.utils;

import com.google.gson.Gson;

/**
 * Create by wjc133
 * Date: 2016/1/5
 * Time: 17:41
 */
public class JsonUtils {
    private static Gson gson = new Gson();

    private JsonUtils() {

    }

    public static <T> T fromString(String data, Class<T> clazz) {
        return gson.fromJson(data, clazz);
    }

    public static <T> T fromBytes(byte[] data, Class<T> clazz) {
        return fromString(new String(data), clazz);
    }
}
