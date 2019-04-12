package com.jwzhu.platform.core.service.param;

import javax.validation.constraints.NotNull;

import com.jwzhu.platform.core.service.bean.ServiceBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class ServiceUpdateParam extends BaseParam<ServiceBean> {

    @NotNull(message = "ID不能为空")
    private Long id;
    private String serviceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
