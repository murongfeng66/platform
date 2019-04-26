package com.jwzhu.platform.core.service.model;

import java.time.LocalDateTime;

import com.jwzhu.platform.common.enums.AvailableStatusEscaper;
import com.jwzhu.platform.plugs.jsonEscape.bind.ShortEscape;

public class Service {

    private Long id;
    private String serviceCode;
    private String serviceName;
    private Long ownerId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @ShortEscape(AvailableStatusEscaper.class)
    private Short serviceStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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

    public Short getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Short serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
