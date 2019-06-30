package com.jwzhu.platform.permission;

import java.util.Collection;

import com.jwzhu.platform.common.web.RequestInfo;
import com.jwzhu.platform.plugs.cache.base.CacheUtil;

public class PermissionCheckService implements PermissionService {

    private CacheUtil cacheUtil;

    PermissionCheckService(CacheUtil cacheUtil) {
        this.cacheUtil = cacheUtil;
    }

    @Override
    public boolean checkNoPermission(String url) {
        String cacheKey = getCacheKey(RequestInfo.getRequestUser().getId(), PermissionCacheType.Url);
        return !cacheUtil.sExists(cacheKey, url);
    }

    @Override
    public Collection<String> getPermissions(PermissionCacheType type) {
        return cacheUtil.sMembers(getCacheKey(RequestInfo.getRequestUser().getId(), type));
    }
}
