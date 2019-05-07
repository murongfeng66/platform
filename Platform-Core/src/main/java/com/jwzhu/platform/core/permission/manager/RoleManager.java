package com.jwzhu.platform.core.permission.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.permission.bean.GetRoleResourceBean;
import com.jwzhu.platform.core.permission.bean.PermissionSaveBean;
import com.jwzhu.platform.core.permission.bean.RoleBean;
import com.jwzhu.platform.core.permission.bean.RoleListBean;
import com.jwzhu.platform.core.permission.model.Resource;
import com.jwzhu.platform.core.permission.model.Role;
import com.jwzhu.platform.core.permission.service.RoleService;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;

@Service
public class RoleManager {

    @Autowired
    private RoleService roleService;

    @Transactional
    public void insert(RoleBean bean){
        roleService.insert(bean);
    }

    public List<Role> queryByParam(RoleListBean bean){
        return roleService.queryByParam(bean);
    }

    public Role getById(long id){
        Role role = roleService.getById(id);
        if(role == null){
            throw new BusinessException("无此角色");
        }
        return role;
    }

    public void updateById(RoleBean bean) {
        roleService.updateById(bean);
    }

    @Transactional
    public void savePermission(PermissionSaveBean bean){
        roleService.savePermission(bean);
    }

    public List<Resource> getRoleResource(GetRoleResourceBean bean) {
        return roleService.getRoleResource(bean);
    }

    public List<Role> getHaveRole() {
        return roleService.getHaveRole(RequestBaseParam.getRequestUser().getId());
    }

    public void disable(LongBean bean){
        roleService.disable(bean);
    }

    public void enable(LongBean bean){
        roleService.enable(bean);
    }

    public void delete(LongBean bean){
        roleService.delete(bean);
    }

    public List<Resource> getHaveResource(LongBean bean) {
        return roleService.getHaveResource(bean);
    }
}
