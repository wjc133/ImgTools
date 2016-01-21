package com.elite.tools.imgtools;

/**
 * Create by wjc133
 * Date: 2015/12/30
 * Time: 22:00
 */
public class HttpVisitor {

    public static RequestQueue newRequestQueue(NetWorker worker) {
        if (worker == null) {
            worker = new NormalNetWorker();
        }
        RequestQueue queue = new RequestQueue(worker);
        queue.start();
        return queue;
    }

    public static RequestQueue newRequestQueue() {
        return newRequestQueue(null);
    }

    private HttpVisitor() {
    }

}
