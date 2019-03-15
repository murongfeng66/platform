package com.jwzhu.platform.plugs.web.response;

public class WebResult<T> {

    private T data;
    private Short code;
    private Long costTime;
    private String message;
    private String token;

    public WebResult() {
        this.message = "请求成功";
        code = ResponseCode.SUCCESS.getCode();
    }

    public WebResult(T data) {
        if(data instanceof String){
            this.message = (String) data;
        }else{
            this.data = data;
            this.message = "请求成功";
        }
        code = ResponseCode.SUCCESS.getCode();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
