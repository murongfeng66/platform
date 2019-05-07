package com.jwzhu.platform.core.permission.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.permission.bean.GetRoleResourceBean;
import com.jwzhu.platform.core.permission.bean.PermissionSaveBean;
import com.jwzhu.platform.core.permission.bean.RoleBean;
import com.jwzhu.platform.core.permission.bean.RoleListBean;
import com.jwzhu.platform.core.permission.db.RoleDao;
import com.jwzhu.platform.core.permission.model.Resource;
import com.jwzhu.platform.core.permission.model.Role;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public void insert(RoleBean bean) {
        Role role = roleDao.getByCode(bean.getCode());
        if(role != null){
            throw new BusinessException("该角色已存在");
        }

        bean.setCreateTime(bean.getCreateTime() == null ? RequestBaseParam.getRequestTime() : bean.getCreateTime());
        bean.setRoleStatus(bean.getRoleStatus() == null ? AvailableStatus.Enable.getCode(): bean.getRoleStatus());
        roleDao.insert(bean);
    }

    public List<Role> queryByParam(RoleListBean bean){
        return roleDao.queryByParam(bean);
    }

    public Role getById(long id){
        return roleDao.getById(id);
    }

    public void updateById(RoleBean bean) {
        bean.setUpdateTime(bean.getUpdateTime() == null ? RequestBaseParam.getRequestTime() : bean.getUpdateTime());
        if (roleDao.updateById(bean) == 0) {
            throw new BusinessException("更新失败");
        }
    }

    public void savePermission(PermissionSaveBean bean){
        bean.setCreateTime(bean.getCreateTime() == null ? RequestBaseParam.getRequestTime() : bean.getCreateTime());
        roleDao.deleteRoleResource(bean);
        if(bean.getResourceCodes().size() != 0){
            roleDao.batchInsertRoleResource(bean);
        }
    }

    public List<Resource> getRoleResource(GetRoleResourceBean bean) {
        bean.setResourceStatus(AvailableStatus.Enable.getCode());
        return roleDao.getRoleResource(bean);
    }

    public List<Role> getHaveRole(Long adminId) {
        return roleDao.getHaveRole(adminId, AvailableStatus.Enable.getCode());
    }

    private void updateStatus(UpdateStatusBean bean, String errorMessage){
        bean.setUpdateTime(bean.getUpdateTime() == null ? RequestBaseParam.getRequestTime() : bean.getUpdateTime());
        if(roleDao.updateStatus(bean) == 0){
            throw new BusinessException(errorMessage);
        }
    }

    public void disable(LongBean bean){
        UpdateStatusBean statusBean = new UpdateStatusBean();
        statusBean.setId(bean.getId());
        statusBean.setOldStatus(AvailableStatus.Enable.getCode());
        statusBean.setNewStatus(AvailableStatus.Disable.getCode());
        updateStatus(statusBean, "禁用角色失败");
    }

    public void enable(LongBean bean){
        UpdateStatusBean statusBean = new UpdateStatusBean();
        statusBean.setId(bean.getId());
        statusBean.setOldStatus(AvailableStatus.Disable.getCode());
        statusBean.setNewStatus(AvailableStatus.Enable.getCode());
        updateStatus(statusBean, "启用角色失败");
    }

    public void delete(LongBean bean){
        UpdateStatusBean statusBean = new UpdateStatusBean();
        statusBean.setId(bean.getId());
        statusBean.setOldStatus(AvailableStatus.Disable.getCode());
        statusBean.setNewStatus(AvailableStatus.Delete.getCode());
        updateStatus(statusBean, "删除角色失败");
    }

    public List<Resource> getHaveResource(LongBean bean) {
        return roleDao.getHaveResource(RequestBaseParam.getRequestUser().getId(), AvailableStatus.Enable.getCode());
    }
}
