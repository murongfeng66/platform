package com.jwzhu.platform.core.admin.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.core.admin.bean.AdminListBean;
import com.jwzhu.platform.core.admin.model.Admin;
import com.jwzhu.platform.core.admin.service.AdminService;

@Service
public class AdminManager {

    @Autowired
    private AdminService adminService;

    public List<Admin> queryByParam(AdminListBean bean){
        return adminService.queryByParam(bean);
    }

}
