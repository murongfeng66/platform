package com.jwzhu.platform.core.service.manager;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.service.bean.ServiceBean;
import com.jwzhu.platform.core.service.bean.ServiceListBean;
import com.jwzhu.platform.core.service.bean.ServiceMemberBean;
import com.jwzhu.platform.core.service.bean.ServiceMemberListBean;
import com.jwzhu.platform.core.service.model.Service;
import com.jwzhu.platform.core.service.model.ServiceEscaper;
import com.jwzhu.platform.core.service.service.ServiceService;
import com.jwzhu.platform.plugs.jsonEscape.base.JsonEscapeCacheUtil;

@org.springframework.stereotype.Service
public class ServiceManager {

    @Autowired
    private ServiceService serviceService;
    @Resource
    private JsonEscapeCacheUtil jsonEscapeCacheUtil;

    @Transactional
    public void insert(ServiceBean bean) {
        serviceService.insert(bean);
    }

    public void updateById(ServiceBean bean) {
        serviceService.updateById(bean);
        jsonEscapeCacheUtil.delete(ServiceEscaper.class, bean.getId().toString());
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

    public void queryMember(ServiceMemberListBean bean){
        serviceService.queryMember(bean);
    }

    public void addMember(ServiceMemberBean bean){
        bean.setCreateTime(bean.getCreateTime() == null ? LocalDateTime.now() : bean.getCreateTime());
        serviceService.addMember(bean);
    }

    public void removeMember(ServiceMemberBean bean){
        serviceService.removeMember(bean);
    }

    public void disable(LongBean bean){
        serviceService.disable(bean);
    }

    public void enable(LongBean bean){
        serviceService.enable(bean);
    }

    public void delete(LongBean bean){
        serviceService.delete(bean);
    }

}
