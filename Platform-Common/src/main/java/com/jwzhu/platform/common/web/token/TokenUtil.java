package com.jwzhu.platform.common.web.token;

public interface TokenUtil {

    String createToken(TokenSubject subject);

    boolean checkToken(String token);

    TokenSubject analyzeToken(String token);

}
