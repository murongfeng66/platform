package com.jwzhu.platform.common.cache;

public interface CacheUtil {

    void set(String key, Object value);

    void set(String key, Object value, long expiredTimeMill);

    <T> T get(String key, Class<T> clazz);

    String get(String key);

    boolean delete(String key);

}
