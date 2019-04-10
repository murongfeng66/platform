package com.jwzhu.platform.core.role.param;

import javax.validation.constraints.NotNull;

import com.jwzhu.platform.core.resource.bean.ResourceBean;
import com.jwzhu.platform.core.role.bean.RoleBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class RoleUpdateParam extends BaseParam<RoleBean> {

    @NotNull(message = "ID不能为空")
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected RoleBean getBean() {
        return new RoleBean();
    }
}
