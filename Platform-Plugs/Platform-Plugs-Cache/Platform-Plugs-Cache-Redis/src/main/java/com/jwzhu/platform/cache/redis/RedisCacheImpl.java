package com.jwzhu.platform.cache.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
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
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(key);
        operations.set(JSON.toJSONString(value));
    }

    @Override
    public void set(String key, Object value, long expiredTimeMill) {
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(key);
        operations.set(JSON.toJSONString(value), expiredTimeMill, TimeUnit.MILLISECONDS);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(key);
        String value = operations.get();
        return getClassObject(value, clazz);
    }

    @Override
    public String get(String key) {
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(key);
        return operations.get();
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void hSet(String hashName, String hashKey, Object value) {
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(hashName);
        operations.put(hashKey, JSON.toJSONString(value));
    }

    @Override
    public <T> T hGet(String hashName, String hashKey, Class<T> clazz) {
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(hashName);
        String value = operations.get(hashKey);
        return getClassObject(value, clazz);
    }

    @Override
    public void hDel(String hashName, String hashKey) {
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(hashName);
        operations.delete(hashKey);
    }
}
