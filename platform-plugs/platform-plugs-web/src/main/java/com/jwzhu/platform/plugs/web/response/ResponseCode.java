package com.jwzhu.platform.plugs.web.response;

import java.util.*;

import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.exception.ParamException;
import com.jwzhu.platform.common.exception.SystemException;

/**
 * 响应码
 **/
public enum ResponseCode {

    SUCCESS((short) 1, "成功"),
    FAIL_SYSTEM((short)-1, "系统错误",SystemException.class),
    FAIL_PARAM((short)-2, "参数错误",ParamException.class),
    FAIL_BUSINESS((short)-3,"业务错误",BusinessException.class);

    /**
     * 编码
     */
    private short code;
    /**
     * 描述
     */
    private String message;

    private List<Class<? extends Throwable>> exceptionClass;

    @SafeVarargs
    ResponseCode(short code, String message, Class<? extends Throwable>... exceptionClass) {
        this.code = code;
        this.message = message;
        this.exceptionClass = Arrays.asList(exceptionClass);
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
        for (ResponseCode enumItem : ResponseCode.values()) {
            Map<Short, String> item = new HashMap<>();
            item.put(enumItem.code, enumItem.message);
            list.add(item);
        }
    }

    private static Map<Short, String> map = new HashMap<>();

    static {
        for (ResponseCode item : ResponseCode.values()) {
            map.put(item.code, item.message);
        }
    }

    public static String message(short code) {
        return map.get(code);
    }

    public static ResponseCode get(short code) {
        for (ResponseCode item : ResponseCode.values()) {
            if (item.code == code) {
                return item;
            }
        }
        throw new SystemException("无此[" + code + "]枚举");
    }

    public static ResponseCode get(Throwable e){
        for (ResponseCode item : ResponseCode.values()) {
            if (item.exceptionClass.contains(e.getClass())) {
                return item;
            }
        }
        return FAIL_SYSTEM;
    }

}