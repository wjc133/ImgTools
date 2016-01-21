package com.elite.tools.imgtools;

import com.elite.tools.imgtools.exception.HttpVisitorError;
import com.elite.tools.imgtools.exception.NetworkError;
import com.elite.tools.imgtools.exception.ServerError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wjc133.
 * Date: 2016/1/16
 * Time: 21:40
 * Description:普通网络工人，NetWorker的实现
 */
public class NormalNetWorker implements NetWorker {
    private static final String TAG = "NormalNetWorker";

    private static final int CONNECT_TIMEOUT = 8 * 1000;

    private static final int READ_TIMEOUT = 8 * 1000;

    @SuppressWarnings("unchecked")
    @Override
    public NetworkResponse performRequest(Request<?> request) throws HttpVisitorError {
        long startTime = System.currentTimeMillis();
        HttpURLConnection connection = null;
        byte[] data = null;
        Map<String, String> responseHeaders = Collections.EMPTY_MAP;
        int statusCode = 0;
        try {
            connection = buildConnection(request);
            if (connection.getRequestMethod().equals("POST")) {
                sendParams(request, connection);
            }
            statusCode = connection.getResponseCode();
            data = getData(connection.getInputStream());
            responseHeaders = getResponseHeaders(connection);
            if (statusCode < 200 || statusCode > 299) {
                throw new IOException();
            }
            if (statusCode == HttpStatus.SC_NOT_MODIFIED) {
                return new NetworkResponse(HttpStatus.SC_NOT_MODIFIED, data,
                        responseHeaders, true,
                        System.currentTimeMillis() - startTime);
            }
            return new NetworkResponse(statusCode, data, responseHeaders, false, System.currentTimeMillis() - startTime);

        } catch (MalformedURLException e) {
            throw new RuntimeException("Bad URL " + request.getUrl(), e);
        } catch (IOException e) {
            if (data != null && statusCode > 500) {
                NetworkResponse networkResponse = new NetworkResponse(statusCode, data,
                        responseHeaders, false, System.currentTimeMillis() - startTime);
                throw new ServerError(networkResponse);
            } else {
                throw new NetworkError(e);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private Map<String, String> getResponseHeaders(HttpURLConnection connection) {
        Map<String, String> responseHeaders = new HashMap<>();
        Map<String, List<String>> headers = connection.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            StringBuilder valueBuilder = new StringBuilder();
            for (String v : entry.getValue()) {
                valueBuilder.append(v);
                valueBuilder.append(";");
            }
            String value = valueBuilder.toString();
            value = value.substring(0, value.length() - 1);
            responseHeaders.put(key, value);
        }
        return responseHeaders;
    }

    private void sendParams(Request<?> request, HttpURLConnection connection) throws IOException {
        OutputStream outputStream = (connection.getOutputStream());
        outputStream.write(request.getBody());
        outputStream.flush();
        outputStream.close();
    }

    private HttpURLConnection buildConnection(Request request) throws IOException {
        URL url = new URL(request.getUrl());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
        httpURLConnection.setReadTimeout(READ_TIMEOUT);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod(getMethod(request.getMethod()));
        httpURLConnection.setUseCaches(false);
        setHeaders(request, httpURLConnection);
        return httpURLConnection;
    }

    @SuppressWarnings("unchecked")
    private void setHeaders(Request request, HttpURLConnection httpURLConnection) {
        httpURLConnection.setRequestProperty("Content-Type", request.getBodyContentType());
        Map<String, String> headers = request.getHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            httpURLConnection.setRequestProperty(key, value);
        }
    }


    private String getMethod(int method) {
        switch (method) {
            case Request.Method.GET:
                return "GET";
            case Request.Method.POST:
                return "POST";
            default:
                return "POST";
        }
    }

    private byte[] getData(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }
}
