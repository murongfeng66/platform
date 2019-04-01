package com.jwzhu.platform.core.role.param;

import com.jwzhu.platform.core.role.bean.RoleListBean;
import com.jwzhu.platform.plugs.web.param.PageParam;

public class RoleListParam extends PageParam<RoleListBean> {

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

    @Override
    protected RoleListBean getBean() {
        return new RoleListBean();
    }
}
