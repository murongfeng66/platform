package com.jwzhu.platform.common.exception;

public class BusinessException extends RuntimeException{

	public BusinessException(Throwable t){
		super(t);
	}

	public BusinessException(String msg){
		super(msg);
	}

	public BusinessException(String msg, Throwable t){
		super(msg, t);
	}
}
