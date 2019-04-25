package com.jwzhu.platform.core.admin.model;

import java.time.LocalDateTime;

import com.jwzhu.platform.common.enums.AvailableStatusEscaper;
import com.jwzhu.platform.core.service.model.ServiceEscaper;
import com.jwzhu.platform.plugs.jsonEscape.bind.LongEscape;
import com.jwzhu.platform.plugs.jsonEscape.bind.ShortEscape;

public class Admin {

    private Long id;
    private String nickname;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @ShortEscape(AvailableStatusEscaper.class)
    private Short adminStatus;
    @LongEscape(value = ServiceEscaper.class, targetNames = {"serviceCode","serviceName"})
    private Long serviceId;
    @ShortEscape(AdminTypeEscaper.class)
    private Short adminType;

    public Short getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Short adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Short getAdminType() {
        return adminType;
    }

    public void setAdminType(Short adminType) {
        this.adminType = adminType;
    }
}
