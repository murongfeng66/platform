package com.jwzhu.platform.core.service.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.service.bean.ServiceBean;
import com.jwzhu.platform.core.service.bean.ServiceListBean;
import com.jwzhu.platform.core.service.model.Service;
import com.jwzhu.platform.core.service.service.ServiceService;

@org.springframework.stereotype.Service
public class ServiceManager {

    @Autowired
    private ServiceService serviceService;

    public void insert(ServiceBean bean) {
        serviceService.insert(bean);
    }

    public void updateById(ServiceBean bean) {
        serviceService.updateById(bean);
    }

    public List<Service> queryByParam(ServiceListBean bean) {
        return serviceService.queryByParam(bean);
    }

    public Service getById(LongBean bean) {
        Service service = serviceService.getById(bean.getId());
        if(service == null){
            throw new BusinessException("无此服务");
        }
        return service;
    }

}
