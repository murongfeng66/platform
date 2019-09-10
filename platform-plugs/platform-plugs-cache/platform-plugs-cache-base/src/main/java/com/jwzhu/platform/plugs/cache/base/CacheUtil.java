package com.jwzhu.platform.plugs.cache.base;

import java.util.Set;

import com.alibaba.fastjson.JSON;

public interface CacheUtil {

    void set(String key, Object value);

    void set(String key, Object value, long expiredTimeMill);

    <T> T get(String key, Class<T> clazz);

    String get(String key);

    void delete(String key);

    void expired(String key, long expiredTimeMill);

    boolean exist(String key);

    void hSet(String hashName, String hashKey, Object value);

    <T> T hGet(String hashName, String hashKey, Class<T> clazz);

    default <T> T getClassObject(String value, Class<T> clazz) {
        if (value == null || value.trim().length() == 0) {
            return null;
        }
        return JSON.parseObject(value, clazz);
    }

    void hDelete(String hashName, String hashKey);

    void sAdd(String key, String... value);

    void sRemove(String key, Object... value);

    boolean sExists(String key, Object value);

    Set<String> sMembers(String key);

    default boolean returnBoolean(Boolean result) {
        return result == null ? false : result;
    }

    long increase(String key, long delta);

    long increase(String key);

    long decrease(String key, long delta);

    long decrease(String key);
}
