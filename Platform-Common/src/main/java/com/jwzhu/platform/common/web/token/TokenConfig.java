package com.jwzhu.platform.common.web.token;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.jwzhu.platform.common.web.token.impl.TokenUtiAES;
import com.jwzhu.platform.common.web.token.impl.TokenUtilJWT;

@Configuration
@ConfigurationProperties(prefix = "com.jwzhu.platform.token", ignoreUnknownFields = false)
public class TokenConfig {

    private Long expiredTime;

    private String secretKey;

    private String aesIv;

    private Type type = Type.AES;

    private String paramName = "Token";

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type{
        AES{
            @Override
            public TokenUtil getInstance(TokenConfig tokenConfig) {
                return new TokenUtiAES(tokenConfig);
            }
        },JWT{
            @Override
            public TokenUtil getInstance(TokenConfig tokenConfig) {
                return new TokenUtilJWT(tokenConfig);
            }
        };
        public abstract TokenUtil getInstance(TokenConfig tokenConfig);
    }

    public String getAesIv() {
        return aesIv;
    }

    public void setAesIv(String aesIv) {
        this.aesIv = aesIv;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
