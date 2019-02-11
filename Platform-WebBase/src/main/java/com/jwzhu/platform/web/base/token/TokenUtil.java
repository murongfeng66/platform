package com.jwzhu.platform.web.base.token;

public interface TokenUtil {

    String createToken(TokenSubject subject);

    TokenSubject checkToken(String token);

    TokenSubject analyzeToken(String token);

}
