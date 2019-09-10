package com.jwzhu.platform.common.enums;

import java.util.HashMap;
import java.util.Map;

import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscapeInterface;

/**
 * 可用性
 **/
public enum AvailableStatus implements JsonEscapeInterface<Short> {

    Enable((short) 1, "启用"),
    Disable((short) -1, "禁用"),
    Delete((short) -2, "删除");

    public static Map<Short, String> map = new HashMap<>();

    static {
        for (AvailableStatus item : AvailableStatus.values()) {
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

    AvailableStatus(short code, String message) {
        this.code = code;
        this.message = message;
    }

    public static AvailableStatus get(short code) {
        for (AvailableStatus item : AvailableStatus.values()) {
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
        return map.get(code);
    }
}