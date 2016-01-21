package com.elite.tools.imgtools.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by wjc133
 * Date: 2016/1/4
 * Time: 20:37
 */
public class RequestParams {
    private Map<String, Object> params;

    public RequestParams() {
        params = new HashMap<>();
    }

    public Map<String, Object> getParams() {
        return params;

    }
}
