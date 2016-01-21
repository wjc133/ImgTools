package com.elite.tools.imgtools;

/**
 * Create by wjc133
 * Date: 2016/1/4
 * Time: 19:39
 */
public class RequestManager {
    private static RequestManager instance;

    private RequestManager() {

    }

    public static RequestManager getInstance() {
        if (instance == null) {
            synchronized (RequestManager.class) {
                if (instance == null) {
                    instance = new RequestManager();
                }
            }
        }
        return instance;
    }

}
