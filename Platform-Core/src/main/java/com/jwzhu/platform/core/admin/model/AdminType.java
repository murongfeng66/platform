package com.jwzhu.platform.core.admin.model;

import java.util.HashMap;
import java.util.Map;

import com.jwzhu.platform.common.exception.SystemException;

/**
 * 管理员类型
 **/
public enum AdminType {

    Super((short) 1, "超级管理员"),
    ServiceSuper((short) 2, "服务超级管理员"),
    Service((short) 3, "服务管理员");

    /**
     * 编码
     */
    private short code;
    /**
     * 描述
     */
    private String message;

    AdminType(short code, String message) {
        this.code = code;
        this.message = message;
    }

    public short getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private static Map<Short, String> map = new HashMap<>();

    static {
        for (AdminType item : AdminType.values()) {
            map.put(item.code, item.message);
        }
    }

    public static String message(Short code) {
        return map.get(code);
    }

    public static AdminType get(short code) {
        for (AdminType item : AdminType.values()) {
            if (item.code == code) {
                return item;
            }
        }
        throw new SystemException("无此[" + code + "]枚举");
    }

}