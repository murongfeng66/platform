package com.jwzhu.platform.file.model;

import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscapeInterface;
import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscaper;

@JsonEscaper
public class PermissionTypeEscaper implements JsonEscapeInterface<Short> {
    @Override
    public Object getMessage(Short id) {
        return PermissionType.message(id);
    }
}
