package com.jwzhu.platform.common.enums;


import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscapeInterface;
import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscaper;

@JsonEscaper
public class AvailableStatusEscaper implements JsonEscapeInterface<Short> {
    @Override
    public Object getMessage(Short id) {
        return AvailableStatus.message(id);
    }
}
