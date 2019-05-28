package com.jwzhu.platform.permission;

import java.util.Collection;

public interface PermissionService {

    boolean checkNoPermission(String url);

    Collection<String> getPermissions(PermissionCacheType type);

    default String getCacheKey(long userId, PermissionCacheType type) {
        return this.getClass().getSimpleName() + ":" + String.valueOf(userId) + (type == null ? "" : ":" + type.name());
    }

    enum PermissionCacheType {
        Code,
        Url
    }

}
