package com.jwzhu.platform.core.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.admin.bean.AdminListBean;
import com.jwzhu.platform.core.admin.db.AdminDao;
import com.jwzhu.platform.core.admin.model.Admin;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public Admin getById(long id){
        return adminDao.getById(id);
    }

    public List<Admin> queryByParam(AdminListBean bean){
        return adminDao.queryByParam(bean);
    }

}
