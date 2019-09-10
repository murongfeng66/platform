package com.jwzhu.platform.common.enums;

import java.util.HashMap;
import java.util.Map;

import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscapeInterface;

/**
 * 是否枚举
 **/
public enum YesOrNo implements JsonEscapeInterface<Short> {

    Yes((short) 1, "是"),
    No((short) 0, "否");

    public static Map<Short, String> map = new HashMap<>();

    static {
        for (YesOrNo item : YesOrNo.values()) {
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

    YesOrNo(short code, String message) {
        this.code = code;
        this.message = message;
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

    public short getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Object getMessage(Short id) {
        return null;
    }
}