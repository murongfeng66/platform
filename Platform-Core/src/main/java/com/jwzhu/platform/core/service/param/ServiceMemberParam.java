package com.jwzhu.platform.core.service.param;

import javax.validation.constraints.NotNull;

import com.jwzhu.platform.core.service.bean.ServiceMemberBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class ServiceMemberParam extends BaseParam<ServiceMemberBean> {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    protected ServiceMemberBean getBean() {
        return new ServiceMemberBean();
    }
}
