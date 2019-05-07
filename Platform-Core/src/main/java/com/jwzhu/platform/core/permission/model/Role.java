package com.jwzhu.platform.core.permission.model;

import java.time.LocalDateTime;

import com.jwzhu.platform.common.enums.AvailableStatusEscaper;
import com.jwzhu.platform.core.service.model.ServiceEscaper;
import com.jwzhu.platform.plugs.jsonEscape.bind.LongEscape;
import com.jwzhu.platform.plugs.jsonEscape.bind.ShortEscape;

public class Role {

    private Long id;
    private String code;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @ShortEscape(AvailableStatusEscaper.class)
    private Short roleStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Short getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Short roleStatus) {
        this.roleStatus = roleStatus;
    }
}
