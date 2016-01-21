package com.elite.tools.imgtools;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * Created by wjc133.
 * Date: 2016/1/8
 * Time: 17:57
 * Description:未经处理的response返回结果，相当于裸数据。
 */
public class NetworkResponse implements Serializable {
    //网络状态码
    public final int statusCode;
    //原始数据
    public final byte[] data;
    //响应头部
    public final Map<String, String> headers;
    //响应304，表示数据无修改
    public final boolean notModified;
    //网络响应时间,以毫秒为单位
    public final long networkTimeMs;

    public NetworkResponse(int statusCode, byte[] data, Map<String, String> headers, boolean notModified, long networkTimeMs) {
        this.statusCode = statusCode;
        this.data = data;
        this.headers = headers;
        this.notModified = notModified;
        this.networkTimeMs = networkTimeMs;
    }

    public NetworkResponse(int statusCode, byte[] data, Map<String, String> headers, boolean notModified) {
        this(statusCode, data, headers, notModified, 0);
    }

    public NetworkResponse(byte[] data) {
        this(HttpStatus.SC_OK, data, Collections.<String, String>emptyMap(), false, 0);
    }

    public NetworkResponse(byte[] data, Map<String, String> headers) {
        this(HttpStatus.SC_OK, data, headers, false, 0);
    }
}
