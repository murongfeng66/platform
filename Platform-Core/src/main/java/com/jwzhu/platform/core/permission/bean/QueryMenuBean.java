package com.jwzhu.platform.core.permission.bean;

import com.jwzhu.platform.common.bean.BaseBean;

public class QueryMenuBean extends BaseBean {

    private String roleCodes;
    private Short menuShow;
    private Short availableStatus;
    private Short[] types;
    private Long serviceId;

    public String getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
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

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
