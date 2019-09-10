package com.jwzhu.platform.common;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

/**
 * 系统配置
 */
@Configuration
@ConfigurationProperties(prefix = "com.jwzhu.platform", ignoreUnknownFields = false, ignoreInvalidFields = true)
public class SystemConfig {

    /**
     * 后台系统名称
     */
    private String name = "统一管理平台";
    /**
     * 主系统配置
     */
    private Main main;
    /**
     * 子系统配置
     */
    private Sub sub;
    /**
     * URL资源超时时间
     */
    @DurationUnit(ChronoUnit.MILLIS)
    private Duration resourceTimeout = Duration.ofMinutes(30);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Sub getSub() {
        return sub;
    }

    public void setSub(Sub sub) {
        this.sub = sub;
    }

    public Duration getResourceTimeout() {
        return resourceTimeout;
    }

    public void setResourceTimeout(Duration resourceTimeout) {
        this.resourceTimeout = resourceTimeout;
    }

    public static class Main {
        /**
         * 系统Host
         */
        private String host;
        /**
         * 检查登录接口地址
         */
        private String checkLogin = "/checkLogin";

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getCheckLogin() {
            return host + checkLogin;
        }

        public void setCheckLogin(String checkLogin) {
            this.checkLogin = checkLogin;
        }
    }

    public static class Sub {

        /**
         * 系统Host
         */
        private String host;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }
}
