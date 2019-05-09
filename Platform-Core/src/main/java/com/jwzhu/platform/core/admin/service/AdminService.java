package com.jwzhu.platform.core.admin.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.admin.bean.AdminBean;
import com.jwzhu.platform.core.admin.bean.AdminListBean;
import com.jwzhu.platform.core.admin.db.AdminDao;
import com.jwzhu.platform.core.admin.model.Admin;
import com.jwzhu.platform.core.admin.model.AdminType;
import com.jwzhu.platform.core.permission.bean.AdminRoleBean;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;

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
        } else if (AdminType.Admin.getCode() == RequestBaseParam.getRequestUser().getType()) {
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
        bean.setCreateTime(bean.getCreateTime() == null ? RequestBaseParam.getRequestTime() : bean.getCreateTime());
        adminDao.deleteAdminRole(bean);
        adminDao.insertAdminRole(bean);
    }

    public void removeAdminRole(AdminRoleBean bean) {
        adminDao.deleteAdminRole(bean);
    }

}
