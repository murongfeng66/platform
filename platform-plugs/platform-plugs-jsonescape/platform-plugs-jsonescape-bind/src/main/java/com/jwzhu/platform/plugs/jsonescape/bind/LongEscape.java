package com.jwzhu.platform.plugs.jsonescape.bind;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface LongEscape {

    Class<? extends JsonEscapeInterface<Long>> value();

    String[] targetNames() default {};

}
