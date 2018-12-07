package com.jwzhu.platform.common.web.token;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private TokenUtil tokenUtil;
    private TokenConfig tokenConfig;

    public TokenService(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
        tokenUtil = tokenConfig.getType().getInstance(tokenConfig);
    }

    public String createToken(TokenSubject subject){
        return tokenUtil.createToken(subject);
    }

    public boolean checkToken(String token){
        return tokenUtil.checkToken(token);
    }

    public TokenSubject analyzeToken(String token){
        return tokenUtil.analyzeToken(token);
    }

    public TokenConfig getTokenConfig() {
        return tokenConfig;
    }
}
