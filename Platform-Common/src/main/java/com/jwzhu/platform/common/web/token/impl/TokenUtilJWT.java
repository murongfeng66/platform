package com.jwzhu.platform.common.web.token.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jwzhu.platform.common.web.exception.TokenTimeOutException;
import com.jwzhu.platform.common.web.token.TokenConfig;
import com.jwzhu.platform.common.web.token.TokenSubject;
import com.jwzhu.platform.common.web.token.TokenUtil;

public class TokenUtilJWT implements TokenUtil {

    private TokenConfig tokenConfig;

    public TokenUtilJWT(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }

    @Override
    public String createToken(TokenSubject subject) {
        SecretKey key = generalKey();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder builder = Jwts.builder();
        builder.setId(UUID.randomUUID().toString());
        builder.setIssuedAt(new Date(subject.getCurrentMilli()));
        builder.setSubject(JSON.toJSONString(subject));
        builder.signWith(signatureAlgorithm, key);

        if (tokenConfig.getExpiredTime() != null && tokenConfig.getExpiredTime() >= 0) {
            long expMillis = subject.getCurrentMilli() + tokenConfig.getExpiredTime();
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    private SecretKey generalKey() {
        if(StringUtils.isEmpty(tokenConfig.getSecretKey())){
            throw new IllegalArgumentException("secretKey is null");
        }
        byte[] encodedKey = Base64.decodeBase64(tokenConfig.getSecretKey());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    @Override
    public boolean checkToken(String token) {
        SecretKey key = generalKey();
        try{
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        }catch (ExpiredJwtException e){
            return false;
        }
        return true;
    }

    @Override
    public TokenSubject analyzeToken(String token) {
        SecretKey key = generalKey();
        try{
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            return JSON.parseObject(claims.getSubject(),TokenSubject.class);
        }catch (ExpiredJwtException e){
            throw new TokenTimeOutException("token is overdue");
        }
    }
}
