package com.jwzhu.platform.core.permission.param;

import javax.validation.constraints.NotEmpty;

import com.jwzhu.platform.core.permission.bean.GetRoleResourceBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class GetRoleResourceParam extends BaseParam<GetRoleResourceBean> {

    @NotEmpty(message = "角色编码不能为空")
    private String roleCode;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    protected GetRoleResourceBean getBean() {
        return new GetRoleResourceBean();
    }
}
