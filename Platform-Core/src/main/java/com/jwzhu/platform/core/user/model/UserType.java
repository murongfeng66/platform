package com.jwzhu.platform.core.user.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jwzhu.platform.common.exception.SystemException;

/**
 * 用户类型
 **/
public enum UserType {

    Admin((short) 1, "管理员");

    /**
     * 编码
     */
    private short code;
    /**
     * 描述
     */
    private String message;

    UserType(short code, String message) {
        this.code = code;
        this.message = message;
    }

    public short getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 枚举键值对列表
     */
    public static List<Map<Short, String>> list = new ArrayList<>();

    static {
        for (UserType enumItem : UserType.values()) {
            Map<Short, String> item = new HashMap<>();
            item.put(enumItem.code, enumItem.message);
            list.add(item);
        }
    }

    private static Map<Short, String> map = new HashMap<>();

    static {
        for (UserType item : UserType.values()) {
            map.put(item.code, item.message);
        }
    }

    public static String message(short code) {
        return map.get(code);
    }

    public static UserType get(short code) {
        for (UserType item : UserType.values()) {
            if (item.code == code) {
                return item;
            }
        }
        throw new SystemException("无此[" + code + "]枚举");
    }

}