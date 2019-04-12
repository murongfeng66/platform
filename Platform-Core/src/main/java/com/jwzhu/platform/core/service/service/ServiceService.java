package com.jwzhu.platform.core.service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.util.StringUtil;
import com.jwzhu.platform.core.service.bean.ServiceBean;
import com.jwzhu.platform.core.service.bean.ServiceListBean;
import com.jwzhu.platform.core.service.db.ServiceDao;
import com.jwzhu.platform.core.service.model.Service;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceDao serviceDao;

    public void insert(ServiceBean bean) {
        bean.setCreateTime(bean.getCreateTime() == null ? LocalDateTime.now() : bean.getCreateTime());
        bean.setAvailableStatus(bean.getAvailableStatus() == null ? AvailableStatus.Enable.getCode() : bean.getAvailableStatus());
        bean.setServiceCode(bean.getServiceCode() == null ? StringUtil.create("PS") : bean.getServiceCode());
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

}
