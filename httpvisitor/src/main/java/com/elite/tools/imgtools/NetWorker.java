package com.elite.tools.imgtools;

import com.elite.tools.imgtools.exception.HttpVisitorError;

/**
 * Create by wjc133
 * Date: 2016/1/7
 * Time: 11:30
 * 网络工作类，负责网络请求
 */
public interface NetWorker {

    NetworkResponse performRequest(Request<?> request) throws HttpVisitorError;
}
