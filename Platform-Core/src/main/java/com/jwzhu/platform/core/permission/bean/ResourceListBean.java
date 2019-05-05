package com.jwzhu.platform.core.permission.bean;

import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.core.permission.model.ResourceList;

public class ResourceListBean extends PageBean<ResourceList> {

    private String key;
    private Short type;
    private Short menuShow;
    private Short availableStatus;
    private Long serviceId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getMenuShow() {
        return menuShow;
    }

    public void setMenuShow(Short menuShow) {
        this.menuShow = menuShow;
    }

    public Short getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(Short availableStatus) {
        this.availableStatus = availableStatus;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
