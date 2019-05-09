package com.jwzhu.platform.core.permission.param;

import javax.validation.constraints.NotEmpty;

import com.jwzhu.platform.core.permission.bean.GetMyResourceBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class GetMyResourceParam extends BaseParam<GetMyResourceBean> {

    @NotEmpty(message = "角色编码不能为空")
    private String roleCode;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    protected GetMyResourceBean getBean() {
        return new GetMyResourceBean();
    }
}
