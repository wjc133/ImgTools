package com.elite.tools.imgtools;

import com.elite.tools.imgtools.exception.HttpVisitorError;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wjc133.
 * Date: 2016/1/10
 * Time: 18:43
 * Description:Response分发的实现,用到了Executor框架
 */
public class ExecutorDelivery implements ResponseDelivery {
    private final Executor mResponsePoster;
    private final ExecutorService executorService = Executors.newFixedThreadPool(25);

    public ExecutorDelivery() {
        this.mResponsePoster = new Executor() {
            @Override
            public void execute(Runnable command) {
                executorService.execute(command);
            }
        };
    }

    public ExecutorDelivery(Executor executor) {
        this.mResponsePoster = executor;
    }

    /**
     * 解析一个response并分发
     *
     * @param request
     * @param response
     */
    @Override
    public void postResponse(Request<?> request, Response<?> response) {
        postResponse(request, response, null);
    }

    /**
     * 解析一个response并分发，这里的runnable将在分发之后被执行
     *
     * @param request
     * @param response
     * @param runnable
     */
    @Override
    public void postResponse(Request<?> request, Response<?> response, Runnable runnable) {
        request.markDeliverd();
        mResponsePoster.execute(new ResponseDeliveryRunnable(request, response, runnable));
    }

    /**
     * 传出一个对应Request的error
     *
     * @param request
     * @param error
     */
    @Override
    public void postError(Request<?> request, HttpVisitorError error) {
        Response<?> response = Response.error(error);
        mResponsePoster.execute(new ResponseDeliveryRunnable(request, response, null));
    }

    private class ResponseDeliveryRunnable implements Runnable {
        private final Request mRequest;
        private final Response mResponse;
        private final Runnable mRunnable;

        public ResponseDeliveryRunnable(Request request, Response response, Runnable runnable) {
            this.mRequest = request;
            this.mResponse = response;
            this.mRunnable = runnable;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            if (mRequest.isCanceled()) {
                mRequest.finish("canceled-at-delivery");
                return;
            }

            if (mResponse.isSuccess()) {
                mRequest.deliverResponse(mResponse.result);
            } else {
                mRequest.deliverError(mResponse.error);
            }

            if (mRunnable != null) {
                mRunnable.run();
            }
        }
    }
}
