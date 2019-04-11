package com.jwzhu.platform.core.resource.model;

import com.jwzhu.platform.plugs.jsonEscape.base.JsonEscaper;
import com.jwzhu.platform.plugs.jsonEscape.bind.JsonEscapeInterface;

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