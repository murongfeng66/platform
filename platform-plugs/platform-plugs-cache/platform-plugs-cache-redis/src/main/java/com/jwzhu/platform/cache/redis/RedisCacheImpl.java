package com.jwzhu.platform.cache.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jwzhu.platform.plugs.cache.base.CacheUtil;

@Component
public class RedisCacheImpl implements CacheUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, Object value) {
        stringRedisTemplate.boundValueOps(key).set(JSON.toJSONString(value));
    }

    @Override
    public void set(String key, Object value, long expiredTimeMill) {
        stringRedisTemplate.boundValueOps(key).set(JSON.toJSONString(value), expiredTimeMill, TimeUnit.MILLISECONDS);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        return getClassObject(value, clazz);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void expired(String key, long expiredTimeMill) {
        stringRedisTemplate.expire(key, expiredTimeMill, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean exist(String key) {
        return returnBoolean(stringRedisTemplate.hasKey(key));
    }

    @Override
    public void hSet(String hashName, String hashKey, Object value) {
        stringRedisTemplate.boundHashOps(hashName).put(hashKey, JSON.toJSONString(value));
    }

    @Override
    public <T> T hGet(String hashName, String hashKey, Class<T> clazz) {
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(hashName);
        String value = operations.get(hashKey);
        return getClassObject(value, clazz);
    }

    @Override
    public void hDelete(String hashName, String hashKey) {
        stringRedisTemplate.boundHashOps(hashName).delete(hashKey);
    }

    @Override
    public void sAdd(String key, String... value) {
        stringRedisTemplate.boundSetOps(key).add(value);
    }

    @Override
    public void sRemove(String key, Object... value) {
        stringRedisTemplate.boundSetOps(key).remove(value);
    }

    @Override
    public boolean sExists(String key, Object value) {
        return returnBoolean(stringRedisTemplate.boundSetOps(key).isMember(value));
    }

    @Override
    public Set<String> sMembers(String key) {
        return stringRedisTemplate.boundSetOps(key).members();
    }
}
