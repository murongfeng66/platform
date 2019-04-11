package com.jwzhu.platform.plugs.jsonEscape.bind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface LongEscape {

    Class<? extends JsonEscapeInterface<Long>> value();

    String[] targetNames() default {};

}
