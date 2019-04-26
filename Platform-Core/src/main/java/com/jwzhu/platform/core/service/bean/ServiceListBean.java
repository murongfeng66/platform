package com.jwzhu.platform.core.service.bean;

import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.core.service.model.Service;

public class ServiceListBean extends PageBean<Service> {

    private String key;
    private Long ownerId;
    private Short serviceStatus;

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

    public Short getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Short serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
