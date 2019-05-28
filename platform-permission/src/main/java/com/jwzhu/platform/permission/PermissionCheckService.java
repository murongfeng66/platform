package com.jwzhu.platform.permission;

import java.util.Collection;

import com.jwzhu.platform.common.web.RequestBaseParam;
import com.jwzhu.platform.plugs.cache.base.CacheUtil;

public class PermissionCheckService implements PermissionService {

    private CacheUtil cacheUtil;

    PermissionCheckService(CacheUtil cacheUtil) {
        this.cacheUtil = cacheUtil;
    }

    @Override
    public boolean checkNoPermission(String url) {
        String cacheKey = getCacheKey(RequestBaseParam.getRequestUser().getId(), PermissionCacheType.Url);
        return !cacheUtil.sExists(cacheKey, url);
    }

    @Override
    public Collection<String> getPermissions(PermissionCacheType type) {
        return cacheUtil.sMembers(getCacheKey(RequestBaseParam.getRequestUser().getId(), type));
    }
}
