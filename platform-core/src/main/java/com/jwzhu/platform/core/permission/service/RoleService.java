package com.jwzhu.platform.core.permission.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.enums.AdminType;
import com.jwzhu.platform.common.exception.NoPermissionException;
import com.jwzhu.platform.core.permission.bean.GetMyRoleBean;
import com.jwzhu.platform.core.permission.bean.PermissionSaveBean;
import com.jwzhu.platform.core.permission.bean.RoleBean;
import com.jwzhu.platform.core.permission.bean.RoleListBean;
import com.jwzhu.platform.core.permission.db.RoleDao;
import com.jwzhu.platform.core.permission.model.AdminRole;
import com.jwzhu.platform.core.permission.model.Role;
import com.jwzhu.platform.common.web.RequestBaseParam;

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

    public void addPermission(PermissionSaveBean bean) {
        roleDao.deleteRoleResource(bean);
        bean.setCreateTime(bean.getCreateTime() == null ? RequestBaseParam.getRequestTime() : bean.getCreateTime());
        roleDao.insertRoleResource(bean);
    }

    public void removePermission(PermissionSaveBean bean) {
        if(RequestBaseParam.getRequestUser().getType() != AdminType.Super.getCode()){
            List<String> adminRoleCode = roleDao.getAllRoleByAdminId(RequestBaseParam.getRequestUser().getId());
            if(adminRoleCode.contains(bean.getRoleCode())){
                throw new NoPermissionException("不允许修改自己角色的权限");
            }
        }
        roleDao.deleteRoleResource(bean);
    }

    public List<AdminRole> getAdminRole(GetMyRoleBean bean) {
        if(RequestBaseParam.getRequestUser().getType() != AdminType.Super.getCode()){
            bean.setSelfId(RequestBaseParam.getRequestUser().getId());
        }

        bean.setRoleStatus(AvailableStatus.Enable.getCode());
        List<AdminRole> roles = roleDao.getAdminRole(bean);
        List<String> adminRoleCode = roleDao.getAllRoleByAdminId(bean.getAdminId());
        for (AdminRole role : roles) {
            role.setHave(adminRoleCode.contains(role.getCode()));
        }
        return roles;
    }
}
