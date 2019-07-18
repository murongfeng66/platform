package com.jwzhu.platform.plugs.web.token;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

import com.jwzhu.platform.plugs.web.token.impl.TokenUtiAES;
import com.jwzhu.platform.plugs.web.token.impl.TokenUtilJWT;

@Configuration
@ConfigurationProperties(prefix = "com.jwzhu.platform.token", ignoreUnknownFields = false, ignoreInvalidFields = true)
public class TokenConfig {
    /**
     * Token过期时长（ms），0为不过期
     */
    @DurationUnit(ChronoUnit.MILLIS)
    private Duration expiredTime = Duration.ofMinutes(30);
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

    public Duration getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Duration expiredTime) {
        this.expiredTime = expiredTime;
    }

    public enum Type {
        AES {
            @Override
            public TokenUtil getInstance(TokenConfig tokenConfig) {
                return new TokenUtiAES(tokenConfig);
            }
        },
        JWT {
            @Override
            public TokenUtil getInstance(TokenConfig tokenConfig) {
                return new TokenUtilJWT(tokenConfig);
            }
        };

        public abstract TokenUtil getInstance(TokenConfig tokenConfig);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAesIv() {
        return aesIv;
    }

    public void setAesIv(String aesIv) {
        this.aesIv = aesIv;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}
