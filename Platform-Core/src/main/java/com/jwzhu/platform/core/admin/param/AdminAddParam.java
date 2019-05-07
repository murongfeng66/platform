package com.jwzhu.platform.core.admin.param;

import com.jwzhu.platform.core.admin.bean.AdminBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class AdminAddParam extends BaseParam<AdminBean> {

    private String nickname;
    private Short adminType;

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

    @Override
    protected AdminBean getBean() {
        return new AdminBean();
    }
}
