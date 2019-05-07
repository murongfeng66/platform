package com.jwzhu.platform.core.admin.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.admin.bean.AdminBean;
import com.jwzhu.platform.core.admin.bean.AdminListBean;
import com.jwzhu.platform.core.admin.model.Admin;
import com.jwzhu.platform.core.admin.model.AdminType;
import com.jwzhu.platform.core.admin.service.AdminService;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;

@Service
public class AdminManager {

    @Autowired
    private AdminService adminService;

    public List<Admin> queryByParam(AdminListBean bean){
        return adminService.queryByParam(bean);
    }

    public void insert(AdminBean bean){
        adminService.insert(bean);
    }

    public void updateById(AdminBean bean){
        adminService.updateById(bean);
    }

    public Admin getById(LongBean bean){
        Admin admin = adminService.getById(bean.getId());
        if(admin == null){
            throw new BusinessException("无此管理员");
        }
        return admin;
    }

    public Map<Short, String> getAddAdminType() {
        Map<Short, String> map = new HashMap<>();
        if(RequestBaseParam.getRequestUser().getType() == AdminType.Super.getCode()){
            map.put(AdminType.Super.getCode(), AdminType.Super.getMessage());
            map.put(AdminType.ServiceSuper.getCode(), AdminType.ServiceSuper.getMessage());
            map.put(AdminType.Service.getCode(), AdminType.Service.getMessage());
        }else if(RequestBaseParam.getRequestUser().getType() == AdminType.ServiceSuper.getCode()){
            map.put(AdminType.ServiceSuper.getCode(), AdminType.ServiceSuper.getMessage());
            map.put(AdminType.Service.getCode(), AdminType.Service.getMessage());
        }
        return map;
    }
}
