package com.jwzhu.platform.core.permission.bean;

import java.time.LocalDateTime;
import java.util.List;

import com.jwzhu.platform.common.bean.BaseBean;

public class PermissionSaveBean extends BaseBean {

    private String roleCode;
    private List<String> resourceCodes;
    private LocalDateTime createTime;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public List<String> getResourceCodes() {
        return resourceCodes;
    }

    public void setResourceCodes(List<String> resourceCodes) {
        this.resourceCodes = resourceCodes;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
