package com.jwzhu.platform.plugs.jsonescape.bind;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface BooleanEscape {

    Class<? extends JsonEscapeInterface<Boolean>> value();

    String[] targetNames() default {};

}
