package com.jwzhu.platform.core.resource.bean;

import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.core.resource.model.ResourceList;

public class ResourceListBean extends PageBean<ResourceList> {

    private String key;
    private Short type;
    private Short isShow;
    private Short availableStatus;

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

    public Short getIsShow() {
        return isShow;
    }

    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    public Short getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(Short availableStatus) {
        this.availableStatus = availableStatus;
    }
}
