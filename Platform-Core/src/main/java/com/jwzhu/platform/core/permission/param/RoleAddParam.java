package com.jwzhu.platform.core.permission.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jwzhu.platform.core.permission.bean.RoleBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class RoleAddParam extends BaseParam<RoleBean> {

    @NotEmpty(message = "编码不能为空")
    private String code;
    @NotEmpty(message = "名称不能为空")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
