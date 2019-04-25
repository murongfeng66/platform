package com.jwzhu.platform.core.admin.param;

import com.jwzhu.platform.core.admin.bean.AdminBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class AdminAddParam extends BaseParam<AdminBean> {

    private String nickname;
    private Long serviceId;
    private Short adminType;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
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
