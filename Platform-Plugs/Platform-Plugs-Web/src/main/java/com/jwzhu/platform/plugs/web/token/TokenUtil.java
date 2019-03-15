package com.jwzhu.platform.plugs.web.token;

public interface TokenUtil {

    String createToken(TokenSubject subject);

    TokenSubject checkToken(String token);

    TokenSubject analyzeToken(String token);

}
