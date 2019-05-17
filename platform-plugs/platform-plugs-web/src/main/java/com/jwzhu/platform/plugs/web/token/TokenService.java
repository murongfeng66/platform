package com.jwzhu.platform.plugs.web.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.web.TokenSubject;
import com.jwzhu.platform.plugs.cache.base.CacheUtil;
import com.jwzhu.platform.plugs.web.exception.token.TokenTimeOutException;

@Service
public class TokenService {

    private static Logger logger = LoggerFactory.getLogger(TokenService.class);
    private TokenUtil tokenUtil;
    private TokenConfig tokenConfig;
    @Autowired
    private CacheUtil cacheUtil;

    private String getCacheKey(String token){
        return tokenConfig.getParamName() + ":"+token;
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
        if (subject.getTime() != null && tokenConfig.getExpiredTime().toMillis() > 0 && tokenConfig.getTokenUpdateTime().toMillis() > 0 && System.currentTimeMillis() - subject.getTime() > tokenConfig.getTokenUpdateTime().toMillis()) {
            TokenSubject newSubject = new TokenSubject();
            newSubject.setId(subject.getId());
            newSubject.setType(subject.getType());
            logger.info("刷新Token");
            return createToken(newSubject);
        }
        return null;
    }
}
