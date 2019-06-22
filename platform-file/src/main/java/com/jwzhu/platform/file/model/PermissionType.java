package com.jwzhu.platform.file.model;

import java.util.HashMap;
import java.util.Map;

import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscapeInterface;

/**
 * 文件夹权限类型
 **/
public enum PermissionType implements JsonEscapeInterface<Short> {

    Public((short) 1, "公共"),
    Admin((short) 2, "管理员"),
    User((short) 3, "用户"),
    Other((short) 4, "自定义");

    /**
     * 编码
     */
    private short code;
    /**
     * 描述
     */
    private String message;

    PermissionType(short code, String message) {
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
        for (PermissionType item : PermissionType.values()) {
            map.put(item.code, item.message);
        }
    }

    public static String message(Short code) {
        return map.get(code);
    }

    public static PermissionType get(short code) {
        for (PermissionType item : PermissionType.values()) {
            if (item.code == code) {
                return item;
            }
        }
        throw new SystemException("无此[" + code + "]枚举");
    }

    @Override
    public Object getMessage(Short id) {
        return map.get(id);
    }
}