package com.jwzhu.platform.core.permission.bean;

import com.jwzhu.platform.common.bean.BaseBean;

public class QueryMenuBean extends BaseBean {

    private Long adminId;
    private Short menuShow;
    private Short availableStatus;
    private Short[] types;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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
