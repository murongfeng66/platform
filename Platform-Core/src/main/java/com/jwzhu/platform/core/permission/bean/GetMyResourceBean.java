package com.jwzhu.platform.core.permission.bean;

import com.jwzhu.platform.common.bean.BaseBean;

public class GetMyResourceBean extends BaseBean {

    private Long selfId;
    private String roleCode;
    private Short[] resourceStatus;

    public Long getSelfId() {
        return selfId;
    }

    public void setSelfId(Long selfId) {
        this.selfId = selfId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Short[] getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(Short... resourceStatus) {
        this.resourceStatus = resourceStatus;
    }
}
