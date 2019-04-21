package com.jwzhu.platform.core.service.model;

import javax.annotation.Resource;

import com.jwzhu.platform.core.service.service.ServiceService;
import com.jwzhu.platform.plugs.jsonEscape.base.JsonEscapeCacheInterface;
import com.jwzhu.platform.plugs.jsonEscape.bind.JsonEscapeInterface;
import com.jwzhu.platform.plugs.jsonEscape.bind.JsonEscaper;

@JsonEscaper
public class ServiceEscaper extends JsonEscapeCacheInterface<Long> {

    @Resource
    private ServiceService serviceService;

    @Override
    public Object getFormDB(Long id) {
        String[] message = new String[2];
        Service service = serviceService.getById(id);
        message[0] = service.getServiceCode();
        message[1] = service.getServiceName();
        return message;
    }
}
