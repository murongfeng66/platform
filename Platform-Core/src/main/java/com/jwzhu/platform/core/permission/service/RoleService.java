package com.jwzhu.platform.core.permission.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.permission.bean.PermissionSaveBean;
import com.jwzhu.platform.core.permission.bean.RoleBean;
import com.jwzhu.platform.core.permission.bean.RoleListBean;
import com.jwzhu.platform.core.permission.db.RoleDao;
import com.jwzhu.platform.core.permission.model.Role;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public void insert(RoleBean bean) {
        bean.setCreateTime(bean.getCreateTime() == null ? RequestBaseParam.getRequestTime() : bean.getCreateTime());
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

}
