package com.jwzhu.platform.core.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jwzhu.platform.core.admin.bean.AdminListBean;
import com.jwzhu.platform.core.admin.manager.AdminManager;
import com.jwzhu.platform.core.admin.param.AdminListParam;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;

@RequestMapping("admin")
@Controller
public class AdminController {

    @Autowired
    private AdminManager adminManager;

    @GetMapping("queryByParam")
    @ResponseBody
    @ControllerHandler
    public AdminListBean queryByParam(AdminListParam param) {
        AdminListBean bean = param.initBean();
        adminManager.queryByParam(bean);
        return bean;
    }

}
