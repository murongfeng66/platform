package com.jwzhu.platform.core.service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.util.StringUtil;
import com.jwzhu.platform.core.service.bean.ServiceBean;
import com.jwzhu.platform.core.service.bean.ServiceListBean;
import com.jwzhu.platform.core.service.bean.ServiceMemberBean;
import com.jwzhu.platform.core.service.bean.ServiceMemberListBean;
import com.jwzhu.platform.core.service.db.ServiceDao;
import com.jwzhu.platform.core.service.model.Service;
import com.jwzhu.platform.core.service.model.ServiceMember;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceDao serviceDao;

    public void insert(ServiceBean bean) {
        bean.setServiceCode(bean.getServiceCode() == null ? StringUtil.create("PS") : bean.getServiceCode());
        Service service = serviceDao.getByServiceCode(bean.getServiceCode());
        if(service != null){
            throw new BusinessException("存在相同的服务编码");
        }
        bean.setCreateTime(bean.getCreateTime() == null ? LocalDateTime.now() : bean.getCreateTime());
        bean.setServiceStatus(bean.getServiceStatus() == null ? AvailableStatus.Enable.getCode() : bean.getServiceStatus());
        serviceDao.insert(bean);
    }

    public void updateById(ServiceBean bean) {
        bean.setUpdateTime(bean.getUpdateTime() == null ? LocalDateTime.now() : bean.getUpdateTime());
        if (serviceDao.updateById(bean) == 0) {
            throw new BusinessException("更新失败");
        }
    }

    public List<Service> queryByParam(ServiceListBean bean) {
        return serviceDao.queryByParam(bean);
    }

    public Service getById(long id) {
        return serviceDao.getById(id);
    }

    public List<ServiceMember> queryMember(ServiceMemberListBean bean){
        return serviceDao.queryMember(bean);
    }

    public void addMember(ServiceMemberBean bean){
        bean.setCreateTime(bean.getCreateTime() == null ? LocalDateTime.now() : bean.getCreateTime());
        serviceDao.addMember(bean);
    }

    public void removeMember(ServiceMemberBean bean){
        serviceDao.removeMember(bean);
    }

    public boolean existsMember(ServiceMemberBean bean){
        return serviceDao.existsMember(bean);
    }

    private void updateStatus(UpdateStatusBean bean, String errorMessage){
        if(serviceDao.updateStatus(bean) == 0){
            throw new BusinessException(errorMessage);
        }
    }

    public void disable(LongBean bean){
        UpdateStatusBean statusBean = new UpdateStatusBean();
        statusBean.setId(bean.getId());
        statusBean.setOldStatus(AvailableStatus.Enable.getCode());
        statusBean.setNewStatus(AvailableStatus.Disable.getCode());
        updateStatus(statusBean, "禁用失败");
    }

    public void enable(LongBean bean){
        UpdateStatusBean statusBean = new UpdateStatusBean();
        statusBean.setId(bean.getId());
        statusBean.setOldStatus(AvailableStatus.Disable.getCode());
        statusBean.setNewStatus(AvailableStatus.Enable.getCode());
        updateStatus(statusBean, "启用失败");
    }

    public void delete(LongBean bean){
        UpdateStatusBean statusBean = new UpdateStatusBean();
        statusBean.setId(bean.getId());
        statusBean.setOldStatus(AvailableStatus.Disable.getCode());
        statusBean.setNewStatus(AvailableStatus.Delete.getCode());
        updateStatus(statusBean, "删除失败");
    }

    public List<Service> getHaveService(Long adminId) {
        return serviceDao.getHaveService(adminId);
    }
}
