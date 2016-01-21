package com.elite.tools.imgtools;

import com.elite.tools.imgtools.exception.HttpVisitorError;

/**
 * Created by wjc133.
 * Date: 2016/1/10
 * Time: 18:39
 * Description:用于响应传递分发的接口
 */
public interface ResponseDelivery {
    /**
     * 解析一个response并分发
     */
    void postResponse(Request<?> request, Response<?> response);

    /**
     * 解析一个response并分发，这里的runnable将在分发之后被执行
     */
    void postResponse(Request<?> request, Response<?> response, Runnable runnable);

    /**
     * 传出一个对应Request的error
     */
    void postError(Request<?> request, HttpVisitorError error);
}
