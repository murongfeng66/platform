package com.jwzhu.platform.core.admin.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.common.enums.AdminType;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.web.RequestInfo;
import com.jwzhu.platform.core.admin.bean.AdminBean;
import com.jwzhu.platform.core.admin.bean.AdminListBean;
import com.jwzhu.platform.core.admin.db.AdminDao;
import com.jwzhu.platform.core.admin.model.Admin;
import com.jwzhu.platform.core.permission.bean.AdminRoleBean;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public Admin getById(long id) {
        return adminDao.getById(id);
    }

    public List<Admin> queryByParam(AdminListBean bean) {
        return adminDao.queryByParam(bean);
    }

    public void insert(AdminBean bean) {
        bean.setCreateTime(bean.getCreateTime() == null ? LocalDateTime.now() : bean.getCreateTime());
        bean.setAdminStatus(bean.getAdminStatus() == null ? AvailableStatus.Enable.getCode() : bean.getAdminStatus());
        bean.setAdminType(bean.getAdminType() == null ? AdminType.Admin.getCode() : bean.getAdminType());

        if (AdminType.Super.getCode() == bean.getAdminType()) {
            throw new BusinessException("不允许添加超级管理员");
        } else if (AdminType.Admin.getCode() == RequestInfo.getRequestUser().getType()) {
            throw new BusinessException("无权限添加管理员");
        }

        adminDao.insert(bean);
    }

    public void updateById(AdminBean bean) {
        bean.setUpdateTime(bean.getUpdateTime() == null ? LocalDateTime.now() : bean.getUpdateTime());
        if (adminDao.updateById(bean) == 0) {
            throw new BusinessException("更新管理员失败");
        }
    }

    public void addAdminRole(AdminRoleBean bean) {
        bean.setCreateTime(bean.getCreateTime() == null ? RequestInfo.getRequestTime() : bean.getCreateTime());
        adminDao.deleteAdminRole(bean);
        adminDao.insertAdminRole(bean);
    }

    public void removeAdminRole(AdminRoleBean bean) {
        adminDao.deleteAdminRole(bean);
    }

    private void updateStatus(UpdateStatusBean bean, String errorMessage) {
        bean.setUpdateTime(bean.getUpdateTime() == null ? RequestInfo.getRequestTime() : bean.getUpdateTime());
        if (adminDao.updateStatus(bean) == 0) {
            throw new BusinessException(errorMessage);
        }
    }

    public void disable(LongBean bean) {
        UpdateStatusBean statusBean = new UpdateStatusBean();
        statusBean.setId(bean.getId());
        statusBean.setOldStatus(AvailableStatus.Enable.getCode());
        statusBean.setNewStatus(AvailableStatus.Disable.getCode());
        updateStatus(statusBean, "禁用管理员失败");
    }

    public void enable(LongBean bean) {
        UpdateStatusBean statusBean = new UpdateStatusBean();
        statusBean.setId(bean.getId());
        statusBean.setOldStatus(AvailableStatus.Disable.getCode());
        statusBean.setNewStatus(AvailableStatus.Enable.getCode());
        updateStatus(statusBean, "启用管理员失败");
    }

    public void delete(LongBean bean) {
        UpdateStatusBean statusBean = new UpdateStatusBean();
        statusBean.setId(bean.getId());
        statusBean.setOldStatus(AvailableStatus.Disable.getCode());
        statusBean.setNewStatus(AvailableStatus.Delete.getCode());
        updateStatus(statusBean, "删除管理员失败");
    }

}
