package com.jwzhu.platform.core.resource.model;

import java.util.HashMap;
import java.util.Map;

import com.jwzhu.platform.common.exception.SystemException;

/**
 * 资源类型
 **/
public enum ResourceType {

    Menu((short) 1, "菜单"),
    Page((short) 2, "页面"),
    Function((short) 3, "功能");

    /**
     * 编码
     */
    private short code;
    /**
     * 描述
     */
    private String message;

    ResourceType(){}

    ResourceType(short code, String message) {
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
        for (ResourceType item : ResourceType.values()) {
            map.put(item.code, item.message);
        }
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
}