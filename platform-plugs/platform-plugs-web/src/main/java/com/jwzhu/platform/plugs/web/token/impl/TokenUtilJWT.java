package com.jwzhu.platform.plugs.web.token.impl;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.jwzhu.platform.common.util.StringUtil;
import com.jwzhu.platform.common.web.TokenSubject;
import com.jwzhu.platform.plugs.web.exception.token.TokenTimeOutException;
import com.jwzhu.platform.plugs.web.token.TokenConfig;
import com.jwzhu.platform.plugs.web.token.TokenUtil;

public class TokenUtilJWT implements TokenUtil {

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

        if (tokenConfig.getExpiredTime().toMillis() >= 0) {
            long expMillis = subject.getTime() + tokenConfig.getExpiredTime().toMillis();
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    private SecretKey generalKey() {
        if (StringUtil.isEmpty(tokenConfig.getSecretKey())) {
            throw new IllegalArgumentException("secretKey is null");
        }
        byte[] encodedKey = Base64.getDecoder().decode(tokenConfig.getSecretKey());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    @Override
    public TokenSubject checkToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
            return JSON.parseObject(claims.getSubject(), TokenSubject.class);
        } catch (ExpiredJwtException e) {
            throw new TokenTimeOutException();
        }
    }

    @Override
    public TokenSubject analyzeToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
            return JSON.parseObject(claims.getSubject(), TokenSubject.class);
        } catch (ExpiredJwtException e) {
            throw new TokenTimeOutException();
        }
    }
}
