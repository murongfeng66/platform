package com.jwzhu.platform.core.role.bean;

import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.core.role.model.Role;

public class RoleListBean extends PageBean<Role> {

    private Long id;
    private String key;

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
}
