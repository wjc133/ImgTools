package com.elite.tools.imgtools;

import com.elite.tools.imgtools.exception.HttpVisitorError;
import com.elite.tools.imgtools.utils.InternalUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

/**
 * Created by wjc133.
 * Date: 2016/1/8
 * Time: 17:43
 * Description:请求实体类
 */
public abstract class Request<T> implements Comparable<Request<T>> {

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    /**
     * 各种请求方法的定义，目前仅支持Get和Post
     */
    public interface Method {
        int GET = 0;
        int POST = 1;
    }

    private final String mUrl;
    private final int mMethod;
    // TODO: 2016/1/8 暂时不考虑重定向
    //ID，用于唯一辨别请求
    private String mIdentifier;
    private Response.ErrorListener mErrorListener;
    //序列号，用于判断请求的顺序
    private Integer mSequence;
    private RequestQueue mRequestQueue;
    private boolean mCanceled = false;
    /**
     * 当前response是否已经被分发
     **/
    private boolean mResponseDelivered = false;
    /**
     * tag主要用于批量取消请求
     */
    private Object mTag;

    //为什么只有ErrorListener没有Listener
    //因为Listener返回的Response根据具体场景所包含的data不同

    public Request(String url, int method, Response.ErrorListener errorListener) {
        this.mMethod = method;
        this.mUrl = url;
        this.mIdentifier = createIdentifier(method, url);
        this.mErrorListener = errorListener;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getMethod() {
        return mMethod;
    }

    public Object getTag() {
        return mTag;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    public Response.ErrorListener getErrorListener() {
        return mErrorListener;
    }

    void finish(final String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.finish(this);
            onFinish();
        }
    }

    protected void onFinish() {
        mErrorListener = null;
    }

    public final Request<?> setSequence(int sequence) {
        mSequence = sequence;
        return this;
    }

    public final int getSequence() {
        if (mSequence == null) {
            throw new IllegalStateException("getSequence called before setSequence");
        }
        return mSequence;
    }

    public String getIdentifier() {
        return mIdentifier;
    }

    public void cancel() {
        mCanceled = true;
    }

    public boolean isCanceled() {
        return mCanceled;
    }

    /**
     * 获取请求头部
     **/
    public Map<String, String> getHeaders() {
        return Collections.emptyMap();
    }

    protected Map<String, String> getParams() {
        return null;
    }

    protected String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    /**
     * 用于返回请求体的类型，需要时可重写
     **/
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    public byte[] getBody() {
        //Get请求的参数是在请求头部的，所以不应该放在body中返回
        if (mMethod == 0) {
            return null;
        }
        Map<String, String> params = getParams();
        if (params != null && params.size() > 0) {
            return encodeParameters(params, getParamsEncoding());
        }
        return null;
    }

    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodeParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodeParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodeParams.append("=");
                encodeParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodeParams.append("&");
            }
            return encodeParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public final int getTimesoutMs() {
        return 0;
    }

    private static long sCounter;

    /**
     * sha1(Request:method:url:timestamp:counter)
     *
     * @param method http method
     * @param url    http request url
     * @return sha1 hash string
     */
    private static String createIdentifier(final int method, final String url) {
        return InternalUtils.sha1Hash("Request:" + method + ":" + url +
                ":" + System.currentTimeMillis() + ":" + (sCounter++));
    }

    /**
     * 标记响应已分发
     **/
    public void markDeliverd() {
        mResponseDelivered = true;
    }

    /**
     * 响应是否已经分发
     **/
    public boolean hasHadResponseDeliverd() {
        return mResponseDelivered;
    }

    /**
     * 解析网络原始数据
     **/
    abstract protected Response<T> parseNetworkResponse(NetworkResponse response);

    /**
     * 解析错误
     **/
    public HttpVisitorError parseNetworkError(HttpVisitorError error) {
        return error;
    }

    /**
     * 分发响应
     **/
    abstract protected void deliverResponse(T response);

    public void deliverError(HttpVisitorError error) {
        if (mErrorListener != null) {
            mErrorListener.onErrorResponse(error);
        }
    }

    @Override
    public String toString() {
        return (mCanceled ? "[x]" : "[ ]") + getUrl() + " " + getPriority() + " " + mSequence;
    }

    @Override
    public int compareTo(Request<T> another) {
        Priority left = this.getPriority();
        Priority right = another.getPriority();
        return left == right ? this.mSequence - another.mSequence : right.ordinal() - left.ordinal();
    }
}
