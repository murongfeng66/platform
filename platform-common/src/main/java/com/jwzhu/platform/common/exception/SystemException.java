package com.jwzhu.platform.common.exception;

public class SystemException extends RuntimeException {

    private static final long serialVersionUID = -359445734313798176L;

    public SystemException(Throwable t) {
        super(t);
    }

    public SystemException(String msg) {
        super(msg);
    }

    public SystemException(String msg, Throwable t) {
        super(msg, t);
    }
}
