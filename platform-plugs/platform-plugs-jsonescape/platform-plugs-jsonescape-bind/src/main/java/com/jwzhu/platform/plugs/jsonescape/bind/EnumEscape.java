package com.jwzhu.platform.plugs.jsonescape.bind;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface EnumEscape {

    Class<? extends Enum<?>> value();

    String[] targetNames() default {};

}
