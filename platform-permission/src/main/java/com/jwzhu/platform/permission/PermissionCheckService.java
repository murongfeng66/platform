package com.jwzhu.platform.permission;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jwzhu.platform.common.PlatformConfig;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.util.HttpUtil;
import com.jwzhu.platform.common.web.RequestBaseParam;
import com.jwzhu.platform.plugs.cache.base.CacheUtil;

public class PermissionCheckService implements PermissionService {

    private static Logger logger = LoggerFactory.getLogger(PermissionCheckService.class);
    private CacheUtil cacheUtil;
    private PlatformConfig platformConfig;

    PermissionCheckService(CacheUtil cacheUtil, PlatformConfig platformConfig) {
        this.cacheUtil = cacheUtil;
        this.platformConfig = platformConfig;
    }

    @Override
    public boolean checkPermission(String url) {
        String cacheKey = PermissionService.class.getSimpleName() + ":" + RequestBaseParam.getRequestUser().getId();
        if (cacheUtil.exist(cacheKey)) {
            return cacheUtil.sExists(cacheKey, url);
        } else {
            if (StringUtils.isEmpty(platformConfig.getQueryMyResourceUrl())) {
                throw new SystemException("未设置资源获取接口地址");
            }
            HttpUtil.RequestBean bean = new HttpUtil.RequestBean();
            bean.setUrl(platformConfig.getQueryMyResourceUrl());
            bean.addHeader("token", RequestBaseParam.getRequestToken());
            String result = HttpUtil.doGet(bean);
            JSONObject jsonObject = JSON.parseObject(result);
            if (!jsonObject.containsKey("code") || jsonObject.getInteger("code") != 1) {
                logger.error("请求结果：{}", result);
                throw new SystemException("请求验证资源失败");
            }
            List<String> urls = JSON.parseArray(jsonObject.getString("data"), String.class);
            cacheUtil.sAdd(cacheKey, urls.toArray(new String[]{}));
            cacheUtil.expired(cacheKey, platformConfig.getResourceTime());
            return urls.contains(url);
        }
    }
}
