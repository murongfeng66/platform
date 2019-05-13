package com.jwzhu.platform.common.exception;

public class NoPermissionException extends RuntimeException{

	public NoPermissionException(){
		super("无权限");
	}

	public NoPermissionException(Throwable t){
		super(t);
	}

	public NoPermissionException(String msg){
		super(msg);
	}

	public NoPermissionException(String msg, Throwable t){
		super(msg, t);
	}
}
