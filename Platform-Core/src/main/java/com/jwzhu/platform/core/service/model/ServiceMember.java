package com.jwzhu.platform.core.service.model;

import java.time.LocalDateTime;

import com.jwzhu.platform.core.admin.service.AdminNameEscaper;
import com.jwzhu.platform.plugs.jsonEscape.bind.LongEscape;

public class ServiceMember {

    private Long id;
    private Long serviceId;
    @LongEscape(value = AdminNameEscaper.class, targetNames = "username")
    private Long userId;
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
