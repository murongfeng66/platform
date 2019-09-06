package com.jwzhu.platform.plugs.jsonescape.bind;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ShortEscape{

    Class<? extends JsonEscapeInterface<Short>> value();

    String[] targetNames() default {};

}
