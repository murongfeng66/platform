package com.jwzhu.platform.core.service.param;

import com.jwzhu.platform.core.service.bean.ServiceMemberListBean;
import com.jwzhu.platform.plugs.web.param.PageParam;

public class ServiceMemberListParam extends PageParam<ServiceMemberListBean> {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    protected ServiceMemberListBean getBean() {
        return new ServiceMemberListBean();
    }
}
