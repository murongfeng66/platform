package com.jwzhu.platform.core.permission.bean;

import com.jwzhu.platform.common.bean.BaseBean;

public class GetRoleResourceBean extends BaseBean {

    private Long selfId;
    private String roleCode;
    private Short enableStatusCode;

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

    public Short getEnableStatusCode() {
        return enableStatusCode;
    }

    public void setEnableStatusCode(Short enableStatusCode) {
        this.enableStatusCode = enableStatusCode;
    }
}
