package com.jwzhu.platform.plugs.jsonEscape.bind;

@FunctionalInterface
public interface JsonEscapeInterface<T>{

   Object getMessage(T t);

}
