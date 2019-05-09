package com.jwzhu.platform.core.admin.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jwzhu.platform.core.admin.bean.AdminBean;
import com.jwzhu.platform.core.admin.bean.AdminSaveBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class AdminAddParam extends BaseParam<AdminSaveBean> {

    @NotEmpty(message = "名称不能为空")
    private String nickname;
    @NotNull(message = "类型不能为空")
    private Short adminType;
    @NotEmpty(message = "用户名不能为空")
    private String username;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Short getAdminType() {
        return adminType;
    }

    public void setAdminType(Short adminType) {
        this.adminType = adminType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    protected AdminSaveBean getBean() {
        return new AdminSaveBean();
    }
}
