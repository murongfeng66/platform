package com.jwzhu.platform.core.permission.model;

import java.util.HashMap;
import java.util.Map;

import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscapeInterface;

/**
 * 资源类型
 **/
public enum ResourceType implements JsonEscapeInterface<Short> {

    Menu((short) 1, "菜单"),
    Page((short) 2, "页面"),
    Function((short) 3, "功能");

    public static Map<Short, String> map = new HashMap<>();

    static {
        for (ResourceType item : ResourceType.values()) {
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

    ResourceType(short code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String message(Short code) {
        return map.get(code);
    }

    public static ResourceType get(short code) {
        for (ResourceType item : ResourceType.values()) {
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