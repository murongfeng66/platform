package com.jwzhu.platform.core.service.param;

import javax.validation.constraints.NotEmpty;

import com.jwzhu.platform.core.service.bean.ServiceBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class ServiceAddParam extends BaseParam<ServiceBean> {

    @NotEmpty(message = "服务名称不能为空")
    private String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    protected ServiceBean getBean() {
        return new ServiceBean();
    }
}