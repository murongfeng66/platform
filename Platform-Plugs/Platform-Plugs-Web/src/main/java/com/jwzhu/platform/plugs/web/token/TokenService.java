package com.jwzhu.platform.plugs.web.token;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jwzhu.platform.plugs.web.exception.token.TokenTimeOutException;

@Service
public class TokenService {

    private TokenUtil tokenUtil;
    private TokenConfig tokenConfig;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public TokenService(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
        tokenUtil = tokenConfig.getType().getInstance(tokenConfig);
    }

    public String createToken(TokenSubject subject) {
        String token = tokenUtil.createToken(subject);
        BoundValueOperations<String, String> operations = redisTemplate.boundValueOps("Valid_Token_" + token);
        if (tokenConfig.getExpiredTime() != null && tokenConfig.getExpiredTime() > 0) {
            operations.set(token, tokenConfig.getExpiredTime(), TimeUnit.MILLISECONDS);
        } else {
            operations.set(token);
        }
        return token;
    }

    public TokenSubject checkToken(String token) {
        TokenSubject subject = tokenUtil.checkToken(token);
        BoundValueOperations<String, String> operations = redisTemplate.boundValueOps("Valid_Token_" + token);
        if (StringUtils.isEmpty(operations.get())) {
            throw new TokenTimeOutException("凭证过期");
        }
        return subject;
    }

    public void inValidToken(String token){
        redisTemplate.delete("Valid_Token_" + token);
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
