package com.jwzhu.platform.core.permission.model;

import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscapeInterface;
import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscaper;

/**
 * 资源类型
 **/
@JsonEscaper
public class ResourceTypeEscaper implements JsonEscapeInterface<Short> {

    @Override
    public Object getMessage(Short id) {
        return ResourceType.get(id).getMessage();
    }
}