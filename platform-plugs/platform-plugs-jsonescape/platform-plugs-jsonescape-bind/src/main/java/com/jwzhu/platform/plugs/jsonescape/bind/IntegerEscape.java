package com.jwzhu.platform.plugs.jsonescape.bind;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface IntegerEscape {

    Class<? extends JsonEscapeInterface<Integer>> value();

    String[] targetNames() default {};

}
