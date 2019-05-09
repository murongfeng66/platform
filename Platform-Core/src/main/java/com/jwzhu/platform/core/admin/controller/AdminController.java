package com.jwzhu.platform.core.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.core.admin.bean.AdminListBean;
import com.jwzhu.platform.core.admin.manager.AdminManager;
import com.jwzhu.platform.core.admin.model.Admin;
import com.jwzhu.platform.core.admin.param.AdminAddParam;
import com.jwzhu.platform.core.admin.param.AdminListParam;
import com.jwzhu.platform.core.admin.param.AdminUpdateParam;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.param.LongParam;

@RequestMapping("admin")
@Controller
public class AdminController {

    @Autowired
    private AdminManager adminManager;

    @GetMapping("page")
    @ControllerHandler
    public ModelAndView page(ModelAndView view){
        view.setViewName("admin/admin");
        return view;
    }

    @GetMapping("queryByParam")
    @ResponseBody
    @ControllerHandler
    public AdminListBean queryByParam(AdminListParam param) {
        AdminListBean bean = param.initBean();
        adminManager.queryByParam(bean);
        return bean;
    }

    @PostMapping("add")
    @ResponseBody
    @ControllerHandler
    public String add(AdminAddParam param) {
        adminManager.save(param.initBean());
        return "添加管理员成功";
    }

    @PostMapping("updateById")
    @ResponseBody
    @ControllerHandler
    public String updateById(AdminUpdateParam param) {
        adminManager.updateById(param.initBean());
        return "修改管理员成功";
    }

    @GetMapping("getById")
    @ResponseBody
    @ControllerHandler
    public Admin getById(LongParam param){
        return adminManager.getById(param.initBean());
    }

    @GetMapping("getAddAdminType")
    @ResponseBody
    @ControllerHandler
    public Map<Short, String> getAddAdminType(){
        return adminManager.getAddAdminType();
    }

}
