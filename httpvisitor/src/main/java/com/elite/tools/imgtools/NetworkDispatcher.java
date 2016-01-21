package com.elite.tools.imgtools;

import com.elite.tools.imgtools.exception.HttpVisitorError;

import java.util.concurrent.BlockingQueue;

/**
 * Created by wjc133.
 * Date: 2016/1/10
 * Time: 23:37
 * Description:这个类起到一个枢纽的作用，它将一个网络请求从请求队列中取出，
 * 如果这个请求没有被取消，那么则发起网络请求，并将请求结果处理后返还给Request，由它负责通知给调用方。
 */
public class NetworkDispatcher extends Thread {
    private final BlockingQueue<Request<?>> mQueue;
    private final NetWorker mNetWorker;
    private final ResponseDelivery mDelivery;
    private volatile boolean mQuit = false;

    public NetworkDispatcher(BlockingQueue<Request<?>> queue, NetWorker worker,
                             ResponseDelivery delivery) {
        mQueue = queue;
        mNetWorker = worker;
        mDelivery = delivery;
    }

    public void quit() {
        mQuit = true;
        interrupt();
    }

    @Override
    public void run() {
        Request<?> request;
        while (true) {
            long startTimeMs = System.currentTimeMillis();
            try {
                request = mQueue.take();
            } catch (InterruptedException e) {
                if (mQuit) {
                    return;
                }
                continue;
            }

            try {
                if (request.isCanceled()) {
                    request.finish("network-discard-cancelled");
                    continue;
                }
                NetworkResponse networkResponse = mNetWorker.performRequest(request);
                if (networkResponse.notModified && request.hasHadResponseDeliverd()) {
                    request.finish("not-modified");
                    continue;
                }

                Response<?> response = request.parseNetworkResponse(networkResponse);
                request.markDeliverd();
                mDelivery.postResponse(request, response);
            } catch (HttpVisitorError error) {
                error.setNetworkTimeMs(System.currentTimeMillis() - startTimeMs);
                parseAndDeliverNetworkError(request, error);
            } catch (Exception e) {
                HttpVisitorError error = new HttpVisitorError(e);
                error.setNetworkTimeMs(System.currentTimeMillis() - startTimeMs);
                mDelivery.postError(request, error);
            }
        }
    }

    private void parseAndDeliverNetworkError(Request<?> request, HttpVisitorError error) {
        //这里需要做一步解析网络错误的步骤，如果是捕捉到Exception转换成HttpVisitorError就没有必要
        //再进行解析这个步骤了.
        error = request.parseNetworkError(error);
        mDelivery.postError(request, error);
    }
}
