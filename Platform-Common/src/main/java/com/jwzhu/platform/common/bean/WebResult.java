package com.jwzhu.platform.common.bean;

public class WebResult<T> {
    /**
     * 数据
     */
    private T data;
    /**
     * 响应码
     */
    private Short code;
    /**
     * 耗时
     */
    private Long costTime;
    /**
     * 描述
     */
    private String message;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public WebResult() {
        code = ResponseCode.SUCCESS.getCode();
    }

    public WebResult(T data) {
        this.data = data;
        this.message = "请求成功";
        code = ResponseCode.SUCCESS.getCode();
    }

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
