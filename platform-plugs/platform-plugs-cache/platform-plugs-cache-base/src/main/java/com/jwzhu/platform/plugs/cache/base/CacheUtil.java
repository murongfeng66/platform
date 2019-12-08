package com.jwzhu.platform.plugs.cache.base;

import java.util.Set;

import com.alibaba.fastjson.JSON;

/**
 * @author zhujianwen
 */
public interface CacheUtil {

    /**
     * 设置
     * @param key 键
     * @param value 值
     */
    void set(String key, Object value);

    /**
     * 设置
     * @param key 键
     * @param value 值
     * @param expired 过期毫秒数
     */
    void set(String key, Object value, long expired);

    /**
     * 获取对象值
     * @param key 键
     * @param clazz 值类型的Class对象
     * @param <T> 值类型的范型
     * @return 值对象
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 获取字符串值
     * @param key 键
     * @return 字符串值
     */
    String get(String key);

    /**
     * 删除
     * @param key 键
     */
    void delete(String key);

    /**
     * 设置过期时间
     * @param key 键
     * @param expired 过期毫秒数
     */
    void expired(String key, long expired);

    /**
     * 是否存在
     * @param key 键
     * @return 是否存在
     */
    boolean exist(String key);

    /**
     * 设置Hash成员
     * @param hashName Hash名称
     * @param hashKey Hash成员键
     * @param value Hash成员值
     */
    void hSet(String hashName, String hashKey, Object value);

    /**
     * 获取Hash成员
     * @param hashName Hash名称
     * @param hashKey Hash成员键
     * @param clazz Hash成员类型的Class对象
     * @param <T> Hash成员的类型范型
     * @return Hash成员对象
     */
    <T> T hGet(String hashName, String hashKey, Class<T> clazz);

    /**
     * 将JSON字符串反序列化为指定对象
     * @param value JSON字符串
     * @param clazz 对象类型
     * @param <T> 对象类型的范型
     * @return 反序列化后的对象
     */
    default <T> T getClassObject(String value, Class<T> clazz) {
        if (value == null || value.trim().length() == 0) {
            return null;
        }
        return JSON.parseObject(value, clazz);
    }

    /**
     * 删除Hash的成员
     * @param hashName Hash名称
     * @param hashKey Hash成员键
     */
    void hDelete(String hashName, String hashKey);

    /**
     * 添加集合成员
     * @param key 集合名称
     * @param value 集合成员数组
     */
    void sAdd(String key, String... value);

    /**
     * 删除集合成员
     * @param key 集合名称
     * @param value 集合成员数组
     */
    void sRemove(String key, Object... value);

    /**
     * 集合是否存在执行成员
     * @param key 集合名称
     * @param value 成员
     * @return 是否存在
     */
    boolean sExists(String key, Object value);

    /**
     * 返回集合所有成员
     * @param key 集合名称
     * @return 成员集合
     */
    Set<String> sMembers(String key);

    /**
     * 转义Boolean对象，null为false
     * @param result 结果
     * @return 转义结果
     */
    default boolean returnBoolean(Boolean result) {
        return result != null && result;
    }

    /**
     * 递增指定值
     * @param key 键
     * @param delta 递增的值
     * @return 递增后的值
     */
    long increase(String key, long delta);

    /**
     * 递增1
     * @param key 键
     * @return 递增后的值
     */
    long increase(String key);

    /**
     * 递减
     * @param key 键
     * @param delta 递减的值
     * @return 递减后的值
     */
    long decrease(String key, long delta);

    /**
     * 递减1
     * @param key 键
     * @return 递减后的值
     */
    long decrease(String key);
}

