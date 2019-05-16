package com.jwzhu.platform.plugs.web.token;

import com.jwzhu.platform.common.web.TokenSubject;

public interface TokenUtil {

    String createToken(TokenSubject subject);

    TokenSubject checkToken(String token);

    TokenSubject analyzeToken(String token);

}
