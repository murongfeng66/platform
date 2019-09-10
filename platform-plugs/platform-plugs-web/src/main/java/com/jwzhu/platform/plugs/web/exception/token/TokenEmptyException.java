package com.jwzhu.platform.plugs.web.exception.token;

public class TokenEmptyException extends RuntimeException {

    private static final long serialVersionUID = 3574223200196298031L;

    public TokenEmptyException() {
        super("凭证为空");
    }

    public TokenEmptyException(Throwable t) {
        super(t);
    }

    public TokenEmptyException(String msg) {
        super(msg);
    }

    public TokenEmptyException(String msg, Throwable t) {
        super(msg, t);
    }
}
