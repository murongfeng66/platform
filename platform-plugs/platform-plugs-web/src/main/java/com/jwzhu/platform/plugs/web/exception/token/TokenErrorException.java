package com.jwzhu.platform.plugs.web.exception.token;

public class TokenErrorException extends RuntimeException{

	private static final long serialVersionUID = 998934859849414786L;

	public TokenErrorException(Throwable t){
		super(t);
	}

	public TokenErrorException(String msg){
		super(msg);
	}

	public TokenErrorException(String msg, Throwable t){
		super(msg, t);
	}
}
