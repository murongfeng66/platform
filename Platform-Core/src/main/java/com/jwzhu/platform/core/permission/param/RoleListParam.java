package com.jwzhu.platform.core.permission.param;

import com.jwzhu.platform.core.permission.bean.RoleListBean;
import com.jwzhu.platform.plugs.web.param.PageParam;

public class RoleListParam extends PageParam<RoleListBean> {

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

    @Override
    protected RoleListBean getBean() {
        return new RoleListBean();
    }
}
