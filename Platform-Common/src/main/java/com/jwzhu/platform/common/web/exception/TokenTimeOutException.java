package com.jwzhu.platform.common.web.exception;

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
