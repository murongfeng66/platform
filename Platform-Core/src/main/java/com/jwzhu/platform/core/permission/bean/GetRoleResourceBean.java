package com.jwzhu.platform.core.permission.bean;

import com.jwzhu.platform.common.bean.BaseBean;

public class GetRoleResourceBean extends BaseBean {

    private String roleCode;
    private Short[] resourceStatus;

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
