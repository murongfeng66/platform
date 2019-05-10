package com.jwzhu.platform.core.admin.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.exception.NoPermissionException;
import com.jwzhu.platform.core.admin.bean.AdminBean;
import com.jwzhu.platform.core.admin.bean.AdminListBean;
import com.jwzhu.platform.core.admin.bean.AdminSaveBean;
import com.jwzhu.platform.core.admin.bean.LoginBean;
import com.jwzhu.platform.core.admin.model.Admin;
import com.jwzhu.platform.common.enums.AdminType;
import com.jwzhu.platform.core.admin.service.AdminService;
import com.jwzhu.platform.core.admin.service.LoginService;
import com.jwzhu.platform.core.permission.bean.AdminRoleBean;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;

@Service
public class AdminManager {

    @Autowired
    private AdminService adminService;
    @Autowired
    private LoginService loginService;

    public List<Admin> queryByParam(AdminListBean bean){
        return adminService.queryByParam(bean);
    }

    @Transactional
    public void save(AdminSaveBean bean){
        adminService.insert(bean);
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(bean.getUsername());
        loginBean.setUserId(bean.getId());
        loginService.insert(loginBean);
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
            map.put(AdminType.Admin.getCode(), AdminType.Admin.getMessage());
        }
        return map;
    }

    @Transactional
    public void addAdminRole(AdminRoleBean bean){
        checkTarget(bean.getAdminId());
        adminService.addAdminRole(bean);
    }

    public void removeAdminRole(AdminRoleBean bean){
        checkTarget(bean.getAdminId());
        adminService.removeAdminRole(bean);
    }

    private void checkTarget(long targetAdminId){
        if(RequestBaseParam.getRequestUser().getType() != AdminType.Super.getCode() && RequestBaseParam.getRequestUser().getId() == targetAdminId){
            throw new NoPermissionException("不允许修改自己");
        }
    }

    public void disable(LongBean bean){
        adminService.disable(bean);
    }

    public void enable(LongBean bean){
        adminService.enable(bean);
    }

    public void delete(LongBean bean){
        adminService.delete(bean);
    }
}
