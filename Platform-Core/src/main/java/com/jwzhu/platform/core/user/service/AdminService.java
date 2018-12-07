package com.jwzhu.platform.core.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.user.db.AdminDao;
import com.jwzhu.platform.core.user.model.Admin;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public Admin getById(long id){
        Admin admin = adminDao.getById(id);
        if(admin == null){
            throw new BusinessException("管理员不存在");
        }
        return admin;
    }

}
