package com.jwzhu.platform.common.enums;

import java.util.HashMap;
import java.util.Map;

import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscapeInterface;

/**
 * 管理员类型
 **/
public enum AdminType implements JsonEscapeInterface<Short> {

    Super((short) 1, "超级管理员"),
    Admin((short) 2, "管理员");

    private static Map<Short, String> map = new HashMap<>();

    static {
        for (AdminType item : AdminType.values()) {
            map.put(item.code, item.message);
        }
    }

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

    public static AdminType get(short code) {
        for (AdminType item : AdminType.values()) {
            if (item.code == code) {
                return item;
            }
        }
        throw new SystemException("无此[" + code + "]枚举");
    }

    public short getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Object getMessage(Short id) {
        return map.get(id);
    }
}