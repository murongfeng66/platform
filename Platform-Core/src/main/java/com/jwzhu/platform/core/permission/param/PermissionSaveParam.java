package com.jwzhu.platform.core.permission.param;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jwzhu.platform.core.permission.bean.PermissionSaveBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class PermissionSaveParam extends BaseParam<PermissionSaveBean> {

    @NotEmpty(message = "角色编码不能为空")
    private String roleCode;
    private List<String> resourceCodes;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public List<String> getResourceCodes() {
        return resourceCodes;
    }

    public void setResourceCodes(String resourceCodes) {
        this.resourceCodes = Arrays.asList(resourceCodes.split(","));
    }

    @Override
    protected PermissionSaveBean getBean() {
        return new PermissionSaveBean();
    }
}
