package com.jwzhu.platform.plugs.web.token.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jwzhu.platform.plugs.web.exception.token.TokenTimeOutException;
import com.jwzhu.platform.plugs.web.token.TokenConfig;
import com.jwzhu.platform.common.web.TokenSubject;
import com.jwzhu.platform.plugs.web.token.TokenUtil;

public class TokenUtilJWT implements TokenUtil {

    private static Logger logger = LoggerFactory.getLogger(TokenUtilJWT.class);

    private TokenConfig tokenConfig;

    private SecretKey secretKey;

    public TokenUtilJWT(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
        this.secretKey = generalKey();
    }

    @Override
    public String createToken(TokenSubject subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder builder = Jwts.builder();
        builder.setId(UUID.randomUUID().toString());
        builder.setIssuedAt(new Date(subject.getTime()));
        builder.setSubject(JSON.toJSONString(subject));
        builder.signWith(signatureAlgorithm, this.secretKey);

        if (tokenConfig.getExpiredTime() >= 0) {
            long expMillis = subject.getTime() + tokenConfig.getExpiredTime();
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    private SecretKey generalKey() {
        if(StringUtils.isEmpty(tokenConfig.getSecretKey())){
            throw new IllegalArgumentException("secretKey is null");
        }
        byte[] encodedKey = Base64.getDecoder().decode(tokenConfig.getSecretKey());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    @Override
    public TokenSubject checkToken(String token) {
        try{
            Claims claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
            return JSON.parseObject(claims.getSubject(),TokenSubject.class);
        }catch (ExpiredJwtException e){
            throw new TokenTimeOutException();
        }
    }

    @Override
    public TokenSubject analyzeToken(String token) {
        try{
            Claims claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
            return JSON.parseObject(claims.getSubject(),TokenSubject.class);
        }catch (ExpiredJwtException e){
            throw new TokenTimeOutException();
        }
    }
}
