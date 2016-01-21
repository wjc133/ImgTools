package com.elite.tools.imgtools.business;

/**
 * Created by wjc133
 * Date: 2016/1/22
 * Time: 1:20
 */
public class ServerResult<T> {
    private T data;
    private int code;
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ServerResult{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}

