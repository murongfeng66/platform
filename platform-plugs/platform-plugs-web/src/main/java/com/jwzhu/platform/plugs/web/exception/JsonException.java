package com.jwzhu.platform.plugs.web.exception;

public class JsonException extends RuntimeException {

    private static final long serialVersionUID = -6579541414171016305L;

    private Throwable e;

    public JsonException(Throwable e) {
        super(e);
        this.e = e;
    }

    public Throwable getE() {
        return e;
    }

    public void setE(Throwable e) {
        this.e = e;
    }
}
