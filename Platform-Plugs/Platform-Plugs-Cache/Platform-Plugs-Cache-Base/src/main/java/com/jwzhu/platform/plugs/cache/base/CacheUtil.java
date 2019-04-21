package com.jwzhu.platform.plugs.cache.base;

import com.alibaba.fastjson.JSON;

public interface CacheUtil {

    void set(String key, Object value);

    void set(String key, Object value, long expiredTimeMill);

    <T> T get(String key, Class<T> clazz);

    String get(String key);

    void delete(String key);

    void hSet(String hashName, String hashKey, Object value);

    <T> T hGet(String hashName, String hashKey, Class<T> clazz);

    default <T> T getClassObject(String value, Class<T> clazz) {
        if (value == null || value.trim().length() == 0) {
            return null;
        }
        return JSON.parseObject(value, clazz);
    }

    void hDel(String hashName, String hashKey);

    default String getJsonEscapeCacheHashName(Class<?> clazz) {
        return "JsonEscapeCache:" + clazz.getSimpleName();
    }

}
