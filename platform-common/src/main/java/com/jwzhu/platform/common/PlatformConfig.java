package com.jwzhu.platform.common;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.jwzhu.platform", ignoreUnknownFields = false, ignoreInvalidFields = true)
public class PlatformConfig {

    /**
     * 后台系统名称
     */
    private String name = "统一管理平台";
    /**
     * 登录地址
     */
    private String loginUrl = "/";
    /**
     * URL资源超时时间
     */
    @DurationUnit(ChronoUnit.MILLIS)
    private Duration resourceTimeout = Duration.ofMinutes(30);
    /**
     * 平台地址
     */
    private String platformDomain;
    /**
     * 资源权限URL接口地址
     */
    private String queryAdminResourceUrl = "/resource/queryAdminResourceUrl";

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

    public Duration getResourceTimeout() {
        return resourceTimeout;
    }

    public void setResourceTimeout(Duration resourceTimeout) {
        this.resourceTimeout = resourceTimeout;
    }

    public String getPlatformDomain() {
        return platformDomain;
    }

    public void setPlatformDomain(String platformDomain) {
        this.platformDomain = platformDomain;
    }

    public String getQueryAdminResourceUrl() {
        return platformDomain + queryAdminResourceUrl;
    }

    public void setQueryAdminResourceUrl(String queryMyResourceUrl) {
        this.queryAdminResourceUrl = queryMyResourceUrl;
    }
}
