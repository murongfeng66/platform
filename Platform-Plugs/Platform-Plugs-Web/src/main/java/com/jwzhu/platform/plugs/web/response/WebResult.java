package com.jwzhu.platform.plugs.web.response;

public class WebResult<T> {

    private T data;
    private Short code;
    private Long costTime;
    private String message;
    private String token;
    private String redirect;
    private boolean showMessage = false;

    public WebResult() {
        this.message = "请求成功";
        code = ResponseCode.SUCCESS.getCode();
    }

    public WebResult(T data) {
        if (data instanceof String) {
            setMessage((String) data);
        } else {
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
        this.showMessage = true;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public boolean getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(boolean showMessage) {
        this.showMessage = showMessage;
    }
}
