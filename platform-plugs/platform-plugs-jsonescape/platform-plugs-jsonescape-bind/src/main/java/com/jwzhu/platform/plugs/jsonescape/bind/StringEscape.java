package com.jwzhu.platform.plugs.jsonescape.bind;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface StringEscape {

    Class<? extends JsonEscapeInterface<String>> value();

    String[] targetNames() default {};

}
