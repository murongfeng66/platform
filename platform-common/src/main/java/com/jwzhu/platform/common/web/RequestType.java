package com.jwzhu.platform.common.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jwzhu.platform.common.exception.SystemException;

/**
 * 请求类型
 **/
public enum RequestType {

    Page((short) 1, "页面"),
    Ajax((short) 2, "Ajax");

    /**
     * 枚举键值对列表
     */
    public static List<Map<Short, String>> list = new ArrayList<>();
    private static Map<Short, String> map = new HashMap<>();

    static {
        for (RequestType enumItem : RequestType.values()) {
            Map<Short, String> item = new HashMap<>();
            item.put(enumItem.code, enumItem.message);
            list.add(item);
        }
    }

    static {
        for (RequestType item : RequestType.values()) {
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

    RequestType(short code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String message(short code) {
        return map.get(code);
    }

    public static RequestType get(short code) {
        for (RequestType item : RequestType.values()) {
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

}