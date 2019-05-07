package com.jwzhu.platform.core.permission.param;

import com.jwzhu.platform.core.permission.bean.GetRoleResourceBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class GetRoleResourceParam extends BaseParam<GetRoleResourceBean> {

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
