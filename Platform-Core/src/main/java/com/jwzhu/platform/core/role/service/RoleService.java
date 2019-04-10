package com.jwzhu.platform.core.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.resource.bean.ResourceBean;
import com.jwzhu.platform.core.role.bean.RoleBean;
import com.jwzhu.platform.core.role.bean.RoleListBean;
import com.jwzhu.platform.core.role.db.RoleDao;
import com.jwzhu.platform.core.role.model.Role;
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

}
