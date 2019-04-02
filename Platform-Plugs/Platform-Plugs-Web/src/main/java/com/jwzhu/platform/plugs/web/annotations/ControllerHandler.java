package com.jwzhu.platform.plugs.web.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口切面<br/>
 * 请求信息打印<br/>
 * 基础参数初始化：请求ID、请求时间<br/>
 * Token解析及验证（Token不为空时）<br/>
 * 异常包装<br/>
 * 参数验证，默认：true<br/>
 * 参数验证组，默认：空<br/>
 * 打印响应，默认：false<br/>
 * Token必需，默认：true<br/>
 * Token清除，默认：false<br/>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerHandler {

    /**
     * 参数验证
     */
    boolean validParam() default true;

    /**
     * 参数验证组
     */
    Class<?>[] validGroups() default {};

    /**
     * 打印响应
     */
    boolean printResponse() default false;

    /**
     * Token必需
     */
    boolean needToken() default true;

    /**
     * Token清除
     */
    boolean clearToken() default false;

}
