package com.elite.tools.imgtools;

import com.elite.tools.imgtools.exception.HttpVisitorError;

/**
 * Created by wjc133.
 * Date: 2016/1/8
 * Time: 17:53
 * Description:
 */
public class Response<T> {
    public final HttpVisitorError error;
    public final T result;

    public interface Listener<T> {
        void onResponse(T response);
    }

    public interface ErrorListener {
        void onErrorResponse(HttpVisitorError exception);
    }


    public Response(HttpVisitorError error) {
        this.result = null;
        this.error = error;
    }

    public Response(T result) {
        this.result = result;
        this.error = null;
    }

    public static <T> Response<T> success(T result) {
        return new Response<T>(result);
    }

    public static <T> Response<T> error(HttpVisitorError error) {
        return new Response<T>(error);
    }

    public boolean isSuccess() {
        return error == null;
    }
}
