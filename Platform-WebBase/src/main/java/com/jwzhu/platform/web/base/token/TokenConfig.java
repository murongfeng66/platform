package com.jwzhu.platform.web.base.token;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.jwzhu.platform.web.base.token.impl.TokenUtiAES;
import com.jwzhu.platform.web.base.token.impl.TokenUtilJWT;

@Configuration
@ConfigurationProperties(prefix = "com.jwzhu.platform.token", ignoreUnknownFields = false, ignoreInvalidFields = true)
public class TokenConfig {
    /**
     * Token过期时长（ms），null或者0为不过期
     */
    private Long expiredTime;
    /**
     * Token生成密钥
     */
    private String secretKey;
    /**
     * AES加密随机数（当Token生成类型为AES时）
     */
    private String aesIv;
    /**
     * Token生成类型
     */
    private Type type = Type.AES;
    /**
     * 在请求中的Token参数名称
     */
    private String paramName = "Token";
    /**
     * 当Token存在过期时长时，刷新Token的时长（ms）
     */
    private Long tokenUpdateTime = 30000L;

    public Long getTokenUpdateTime() {
        return tokenUpdateTime;
    }

    public void setTokenUpdateTime(Long tokenUpdateTime) {
        this.tokenUpdateTime = tokenUpdateTime;
    }

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
