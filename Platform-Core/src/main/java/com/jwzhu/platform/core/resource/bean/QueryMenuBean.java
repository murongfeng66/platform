package com.jwzhu.platform.core.resource.bean;

import com.jwzhu.platform.common.bean.BaseBean;

public class QueryMenuBean extends BaseBean {

    private Long userId;
    private Short menuShow;
    private Short availableStatus;
    private Short[] types;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Short[] getTypes() {
        return types;
    }

    public void setTypes(Short... types) {
        this.types = types;
    }
}
