package com.jwzhu.platform.core.service.bean;

import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.core.service.model.Service;

public class ServiceListBean extends PageBean<Service> {

    private String key;
    private Long ownerId;
    private Short availableStatus;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Short getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(Short availableStatus) {
        this.availableStatus = availableStatus;
    }
}
