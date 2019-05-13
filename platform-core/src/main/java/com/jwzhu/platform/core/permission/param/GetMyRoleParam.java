package com.jwzhu.platform.core.permission.param;

import javax.validation.constraints.NotNull;

import com.jwzhu.platform.core.permission.bean.GetMyRoleBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class GetMyRoleParam extends BaseParam<GetMyRoleBean> {

    @NotNull(message = "管理员ID不能为空")
    private Long adminId;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    @Override
    protected GetMyRoleBean getBean() {
        return new GetMyRoleBean();
    }
}
