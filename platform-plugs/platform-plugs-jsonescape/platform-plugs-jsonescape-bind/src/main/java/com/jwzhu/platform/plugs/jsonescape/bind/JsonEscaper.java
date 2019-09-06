package com.jwzhu.platform.plugs.jsonescape.bind;

import java.lang.annotation.*;

/**
 * JSON转义序列化器注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface JsonEscaper {
}
