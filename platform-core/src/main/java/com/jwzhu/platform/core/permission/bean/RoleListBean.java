package com.jwzhu.platform.core.permission.bean;

import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.core.permission.model.Role;

public class RoleListBean extends PageBean<Role> {

    private Long id;
    private String key;
    private Short roleStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Short getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Short roleStatus) {
        this.roleStatus = roleStatus;
    }
}
