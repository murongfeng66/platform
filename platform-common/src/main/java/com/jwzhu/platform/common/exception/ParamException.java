package com.jwzhu.platform.common.exception;

public class ParamException extends RuntimeException {

    private static final long serialVersionUID = -4032707270159631215L;

    public ParamException(Throwable t) {
        super(t);
    }

    public ParamException(String msg) {
        super(msg);
    }

    public ParamException(String msg, Throwable t) {
        super(msg, t);
    }
}
