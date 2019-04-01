package com.jwzhu.platform.core.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
