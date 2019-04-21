package com.jwzhu.platform.core.service.bean;

import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.core.service.model.ServiceMember;

public class ServiceMemberListBean extends PageBean<ServiceMember> {

    private Long serviceId;
    private Long userId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
