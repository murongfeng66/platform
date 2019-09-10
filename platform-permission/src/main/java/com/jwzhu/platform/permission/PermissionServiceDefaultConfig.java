package com.jwzhu.platform.permission;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jwzhu.platform.plugs.cache.base.CacheUtil;

@Configuration
public class PermissionServiceDefaultConfig {

    @Bean
    @ConditionalOnMissingBean(PermissionService.class)
    public PermissionService permissionService(CacheUtil cacheUtil) {
        return new PermissionCheckService(cacheUtil);
    }

}
