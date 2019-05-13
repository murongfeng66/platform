package com.jwzhu.platform.plugs.jsonescape.bind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ShortEscape{

    Class<? extends JsonEscapeInterface<Short>> value();

    String[] targetNames() default {};

}
