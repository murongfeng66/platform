package com.jwzhu.platform.web.base.exception;

public class JsonException extends RuntimeException{

	private Throwable e;

	public JsonException(Throwable e){
		super(e);
		this.e = e;
	}

	public Throwable getE() {
		return e;
	}

	public void setE(Throwable e) {
		this.e = e;
	}
}
