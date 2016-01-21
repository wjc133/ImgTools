package com.elite.tools.imgtools.exception;

import com.elite.tools.imgtools.NetworkResponse;

/**
 * Created by wjc133.
 * Date: 2016/1/8
 * Time: 17:56
 * Description:Http请求异常类
 */
@SuppressWarnings("serial")
public class HttpVisitorError extends Exception {
    public final NetworkResponse networkResponse;
    private long networkTimeMs;

    public HttpVisitorError() {
        networkResponse = null;
    }

    public HttpVisitorError(NetworkResponse networkResponse) {
        this.networkResponse = networkResponse;
    }

    public HttpVisitorError(String detailMessage) {
        super(detailMessage);
        networkResponse = null;
    }

    public HttpVisitorError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        networkResponse = null;
    }

    public HttpVisitorError(Throwable throwable) {
        super(throwable);
        networkResponse = null;
    }

    public void setNetworkTimeMs(long networkTimeMs) {
        this.networkTimeMs = networkTimeMs;
    }

    public long getNetworkTimeMs() {
        return networkTimeMs;
    }
}
