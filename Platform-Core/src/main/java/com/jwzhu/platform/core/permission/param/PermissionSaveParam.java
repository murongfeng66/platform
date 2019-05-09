package com.jwzhu.platform.core.permission.param;

import javax.validation.constraints.NotEmpty;

import com.jwzhu.platform.core.permission.bean.PermissionSaveBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class PermissionSaveParam extends BaseParam<PermissionSaveBean> {

    @NotEmpty(message = "角色编码不能为空")
    private String roleCode;
    @NotEmpty(message = "资源编码不能为空")
    private String resourceCode;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    @Override
    protected PermissionSaveBean getBean() {
        return new PermissionSaveBean();
    }
}
