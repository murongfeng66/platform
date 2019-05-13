package com.jwzhu.platform.core.admin.param;

import com.jwzhu.platform.core.admin.bean.AdminBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class AdminUpdateParam extends BaseParam<AdminBean> {

    private Long id;
    private String nickname;
    private Short adminType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
