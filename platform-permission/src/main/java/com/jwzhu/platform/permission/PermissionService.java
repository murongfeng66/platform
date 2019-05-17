package com.jwzhu.platform.permission;

public interface PermissionService {

    boolean checkPermission(String url);

    default String getCacheKey(long userId) {
        return this.getClass().getSimpleName() + ":" + String.valueOf(userId);
    }

}
