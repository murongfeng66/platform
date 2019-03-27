package com.jwzhu.platform.plugs.web.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jwzhu.platform.common.cache.CacheUtil;
import com.jwzhu.platform.plugs.web.exception.token.TokenTimeOutException;

@Service
public class TokenService {

    private TokenUtil tokenUtil;
    private TokenConfig tokenConfig;
    @Autowired
    private CacheUtil cacheUtil;

    private String getCacheKey(String token){
        return tokenConfig.getParamName() + "_"+token;
    }

    public TokenService(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
        tokenUtil = tokenConfig.getType().getInstance(tokenConfig);
    }

    public String createToken(TokenSubject subject) {
        String token = tokenUtil.createToken(subject);
        String cacheKey = getCacheKey(token);
        if (tokenConfig.getExpiredTime() != null && tokenConfig.getExpiredTime() > 0) {
            cacheUtil.set(cacheKey, subject, tokenConfig.getExpiredTime());
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

    public void inValidToken(String token){
        cacheUtil.delete(getCacheKey(token));
    }

    public TokenSubject analyzeToken(String token) {
        return tokenUtil.analyzeToken(token);
    }

    public TokenConfig getTokenConfig() {
        return tokenConfig;
    }

    public String updateToken(TokenSubject subject) {
        if (subject.getCreateMillis() != null && tokenConfig.getExpiredTime() != null && tokenConfig.getExpiredTime() > 0 && System.currentTimeMillis() - subject.getCreateMillis() > tokenConfig.getTokenUpdateTime()) {
            TokenSubject newSubject = new TokenSubject(subject.getUserId(), subject.getUserType());
            return createToken(newSubject);
        }
        return null;
    }
}
