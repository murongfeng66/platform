package com.jwzhu.platform.common.exception;

public class ParamException extends RuntimeException{

	public ParamException(Throwable t){
		super(t);
	}

	public ParamException(String msg){
		super(msg);
	}

	public ParamException(String msg, Throwable t){
		super(msg, t);
	}
}
