package com.jwzhu.platform.common.web.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口切面，包含初始化请求参数、参数验证、异常处理
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerHandler {

    boolean validParam() default true;

    Class<?>[] validGroups() default {};

}
