package com.jwzhu.platform.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.jwzhu.platform", ignoreUnknownFields = false, ignoreInvalidFields = true)
public class PlatformConfig {

    private String name = "统一管理平台";
    private String loginUrl = "/";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
