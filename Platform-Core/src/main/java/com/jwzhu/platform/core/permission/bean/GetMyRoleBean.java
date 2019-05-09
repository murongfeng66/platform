package com.jwzhu.platform.core.permission.bean;

import com.jwzhu.platform.common.bean.BaseBean;

public class GetMyRoleBean extends BaseBean {

    private Long selfId;
    private Long adminId;
    private Short[] roleStatus;

    public Long getSelfId() {
        return selfId;
    }

    public void setSelfId(Long selfId) {
        this.selfId = selfId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Short[] getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Short... roleStatus) {
        this.roleStatus = roleStatus;
    }
}
