package com.jwzhu.platform.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jwzhu.platform.common.exception.SystemException;

/**
 * 是否枚举
 **/
public enum YesOrNo {

    Yes((short) 1, "是"),
    No((short) 0, "否");

    /**
     * 编码
     */
    private short code;
    /**
     * 描述
     */
    private String message;

    YesOrNo(short code, String message) {
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
        for (YesOrNo enumItem : YesOrNo.values()) {
            Map<Short, String> item = new HashMap<>();
            item.put(enumItem.code, enumItem.message);
            list.add(item);
        }
    }

    private static Map<Short, String> map = new HashMap<>();

    static {
        for (YesOrNo item : YesOrNo.values()) {
            map.put(item.code, item.message);
        }
    }

    public static String message(short code) {
        return map.get(code);
    }

    public static YesOrNo get(short code) {
        for (YesOrNo item : YesOrNo.values()) {
            if (item.code == code) {
                return item;
            }
        }
        throw new SystemException("无此[" + code + "]枚举");
    }

}