package com.elite.tools.imgtools.exception;

import com.elite.tools.imgtools.NetworkResponse;

/**
 * Created by wjc133.
 * Date: 2016/1/17
 * Time: 1:57
 * Description:
 */
public class ParseError extends HttpVisitorError {
    public ParseError() {
    }

    public ParseError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public ParseError(Throwable cause) {
        super(cause);
    }
}
