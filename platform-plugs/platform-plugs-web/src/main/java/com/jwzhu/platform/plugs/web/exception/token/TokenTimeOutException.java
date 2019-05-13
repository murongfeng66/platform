package com.jwzhu.platform.plugs.web.exception.token;

public class TokenTimeOutException extends RuntimeException{

	public TokenTimeOutException(){
		super("凭证过期");
	}

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
