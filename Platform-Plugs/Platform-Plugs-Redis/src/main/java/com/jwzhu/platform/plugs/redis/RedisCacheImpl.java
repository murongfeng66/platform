package com.jwzhu.platform.plugs.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jwzhu.platform.common.cache.CacheUtil;

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
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return JSON.parseObject(value, clazz);
    }

    @Override
    public String get(String key) {
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(key);
        return operations.get();
    }

    @Override
    public boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }
}
