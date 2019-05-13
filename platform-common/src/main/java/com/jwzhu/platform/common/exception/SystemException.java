package com.jwzhu.platform.common.exception;

public class SystemException extends RuntimeException{

	public SystemException(Throwable t){
		super(t);
	}
	
	public SystemException(String msg){
		super(msg);
	}
	
	public SystemException(String msg, Throwable t){
		super(msg, t);
	}
}
