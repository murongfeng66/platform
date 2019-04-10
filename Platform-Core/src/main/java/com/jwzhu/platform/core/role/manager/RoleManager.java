package com.jwzhu.platform.core.role.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.role.bean.RoleBean;
import com.jwzhu.platform.core.role.bean.RoleListBean;
import com.jwzhu.platform.core.role.model.Role;
import com.jwzhu.platform.core.role.service.RoleService;

@Service
public class RoleManager {

    @Autowired
    private RoleService roleService;

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

}
