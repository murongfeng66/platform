package com.jwzhu.platform.core.admin.param;

import com.jwzhu.platform.core.admin.bean.AdminListBean;
import com.jwzhu.platform.plugs.web.param.PageParam;

public class AdminListParam extends PageParam<AdminListBean> {

    private String key;
    private Short adminStatus;
    private Long serviceId;
    private Short adminType;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Short getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Short adminStatus) {
        this.adminStatus = adminStatus;
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

    @Override
    protected AdminListBean getBean() {
        return new AdminListBean();
    }
}
