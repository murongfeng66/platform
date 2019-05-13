package com.jwzhu.platform.core.permission.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.permission.bean.GetMyRoleBean;
import com.jwzhu.platform.core.permission.bean.PermissionSaveBean;
import com.jwzhu.platform.core.permission.bean.RoleBean;
import com.jwzhu.platform.core.permission.bean.RoleListBean;
import com.jwzhu.platform.core.permission.model.AdminRole;
import com.jwzhu.platform.core.permission.model.Role;
import com.jwzhu.platform.core.permission.service.RoleService;

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

    public void disable(LongBean bean){
        roleService.disable(bean);
    }

    public void enable(LongBean bean){
        roleService.enable(bean);
    }

    public void delete(LongBean bean){
        roleService.delete(bean);
    }

    @Transactional
    public void addPermission(PermissionSaveBean bean){
        roleService.addPermission(bean);
    }

    public void removePermission(PermissionSaveBean bean){
        roleService.removePermission(bean);
    }

    public List<AdminRole> getAdminRole(GetMyRoleBean bean) {
        return roleService.getAdminRole(bean);
    }
}
