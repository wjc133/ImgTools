package com.elite.tools.imgtools.request;


import com.elite.tools.imgtools.NetworkResponse;
import com.elite.tools.imgtools.Request;
import com.elite.tools.imgtools.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wjc133.
 * Date: 2016/1/17
 * Time: 1:32
 * Description:JSON请求
 */
public abstract class JsonRequest<T> extends Request<T> {
    private static final String TAG = "JsonRequest";
    /**
     * Default charset for JSON request.
     */
    protected static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private Response.Listener<T> mListener;
    private Map<String, String> params = new HashMap<>();

    public JsonRequest(String url, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.POST, url, listener, errorListener);
    }

    public JsonRequest(int method, String url, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(url, method, errorListener);
        this.mListener = listener;
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        mListener = null;
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    protected abstract Response<T> parseNetworkResponse(NetworkResponse response);

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    protected String getParamsEncoding() {
        return PROTOCOL_CHARSET;
    }

    public JsonRequest put(String name, String value) {
        params.put(name, value);
        return this;
    }

    public void putAll(Map<String, String> params) {
        this.params = params;
    }
}
