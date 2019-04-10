package com.jwzhu.platform.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jwzhu.platform.common.exception.SystemException;

/**
 * 可用性
 **/
public enum AvailableStatus {

    Enable((short) 1, "启用"),
    Disable((short)-1, "禁用"),
    Delete((short)-2 ,"删除");

    /**
     * 编码
     */
    private short code;
    /**
     * 描述
     */
    private String message;

    AvailableStatus(short code, String message) {
        this.code = code;
        this.message = message;
    }

    public short getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static Map<Short, String> map = new HashMap<>();

    static {
        for (AvailableStatus item : AvailableStatus.values()) {
            map.put(item.code, item.message);
        }
    }

    public static String message(short code) {
        return map.get(code);
    }

    public static AvailableStatus get(short code) {
        for (AvailableStatus item : AvailableStatus.values()) {
            if (item.code == code) {
                return item;
            }
        }
        throw new SystemException("无此[" + code + "]枚举");
    }

}