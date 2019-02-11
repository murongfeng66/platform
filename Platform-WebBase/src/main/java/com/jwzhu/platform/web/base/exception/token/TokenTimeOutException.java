package com.jwzhu.platform.web.base.exception.token;

public class TokenTimeOutException extends RuntimeException{

	public TokenTimeOutException(Throwable t){
		super(t);
	}

	public TokenTimeOutException(String msg){
		super(msg);
	}

	public TokenTimeOutException(String msg, Throwable t){
		super(msg, t);
	}
}
