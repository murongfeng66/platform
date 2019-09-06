package com.jwzhu.platform.plugs.jsonescape.base;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

/**
 * JSON转义序列化器扫描注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(JsonEscaperRegister.class)
public @interface JsonEscaperScan {

    @AliasFor("value")
    String[] basePackages() default {};

    @AliasFor("basePackages")
    String[] value() default {};

}
