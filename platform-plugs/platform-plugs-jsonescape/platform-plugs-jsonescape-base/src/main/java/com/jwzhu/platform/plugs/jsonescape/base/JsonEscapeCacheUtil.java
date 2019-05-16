package com.jwzhu.platform.plugs.jsonescape.base;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jwzhu.platform.plugs.cache.base.CacheUtil;
import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscapeInterface;

@Component
public class JsonEscapeCacheUtil {

    @Resource
    private CacheUtil cacheUtil;

    private String getHashName(Class<? extends JsonEscapeInterface> clazz) {
        return JsonEscapeCacheUtil.class.getSimpleName() + ":" + clazz.getSimpleName();
    }

    public void add(Class<? extends JsonEscapeInterface> clazz, Object key, Object value) {
        cacheUtil.hSet(getHashName(clazz), key.toString(), value);
    }

    public <T> T get(Class<? extends JsonEscapeInterface> clazz, Object key, Class<T> valueClazz) {
        return cacheUtil.hGet(getHashName(clazz), key.toString(), valueClazz);
    }

    public void delete(Class<? extends JsonEscapeInterface> clazz, Object key) {
        cacheUtil.hDelete(getHashName(clazz), key.toString());
    }

}
