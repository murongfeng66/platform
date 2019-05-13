package com.jwzhu.platform.core.admin.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jwzhu.platform.core.permission.bean.AdminRoleBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class AdminRoleSaveParam extends BaseParam<AdminRoleBean> {

    @NotNull(message = "管理员ID不能为空")
    private Long adminId;
    @NotEmpty(message = "角色编码不能为空")
    private String roleCode;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    protected AdminRoleBean getBean() {
        return new AdminRoleBean();
    }
}
