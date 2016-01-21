package com.elite.tools.imgtools.exception;

import com.elite.tools.imgtools.NetworkResponse;

/**
 * Created by wjc133.
 * Date: 2016/1/17
 * Time: 0:46
 * Description: 服务器错误
 */
public class ServerError extends HttpVisitorError {
    public ServerError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public ServerError() {
        super();
    }
}
