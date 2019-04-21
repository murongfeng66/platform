package com.jwzhu.platform.core.admin.service;

import javax.annotation.Resource;

import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.admin.model.Admin;
import com.jwzhu.platform.plugs.jsonEscape.base.JsonEscapeCacheInterface;
import com.jwzhu.platform.plugs.jsonEscape.bind.JsonEscapeInterface;
import com.jwzhu.platform.plugs.jsonEscape.bind.JsonEscaper;

@JsonEscaper
public class AdminNameEscaper extends JsonEscapeCacheInterface<Long> {

    @Resource
    private AdminService adminService;

    @Override
    public Object getFormDB(Long id) {
        Admin admin = adminService.getById(id);
        if(admin == null){
            throw new BusinessException("管理员不存在");
        }
        return admin.getNickname();
    }
}
