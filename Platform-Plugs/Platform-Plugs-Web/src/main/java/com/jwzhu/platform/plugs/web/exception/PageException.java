package com.jwzhu.platform.plugs.web.exception;

public class PageException extends RuntimeException{

	private Throwable e;

	public PageException(Throwable e){
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
