package com.jwzhu.platform.plugs.web.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.web.TokenSubject;
import com.jwzhu.platform.plugs.cache.base.CacheUtil;
import com.jwzhu.platform.plugs.web.exception.token.TokenTimeOutException;

@Service
public class TokenService {

    private TokenUtil tokenUtil;
    private TokenConfig tokenConfig;
    @Autowired
    private CacheUtil cacheUtil;

    public String getCacheKey(String token) {
        return tokenConfig.getParamName() + ":" + token;
    }

    public TokenService(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
        tokenUtil = tokenConfig.getType().getInstance(tokenConfig);
    }

    public String createToken(TokenSubject subject) {
        String token = tokenUtil.createToken(subject);
        String cacheKey = getCacheKey(token);
        if (tokenConfig.getExpiredTime().toMillis() > 0) {
            cacheUtil.set(cacheKey, subject, tokenConfig.getExpiredTime().toMillis());
        } else {
            cacheUtil.set(cacheKey, subject);
        }
        return token;
    }

    public TokenSubject checkToken(String token) {
        TokenSubject subject = tokenUtil.checkToken(token);
        String cacheKey = getCacheKey(token);
        if (cacheUtil.get(cacheKey) == null) {
            throw new TokenTimeOutException();
        }
        return subject;
    }

    public void inValidToken(String token) {
        cacheUtil.delete(getCacheKey(token));
    }

    public TokenSubject analyzeToken(String token) {
        return tokenUtil.analyzeToken(token);
    }

    public TokenConfig getTokenConfig() {
        return tokenConfig;
    }
}
