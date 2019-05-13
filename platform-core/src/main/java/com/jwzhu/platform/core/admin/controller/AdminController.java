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
import com.jwzhu.platform.core.admin.param.AdminRoleSaveParam;
import com.jwzhu.platform.core.admin.param.AdminUpdateParam;
import com.jwzhu.platform.core.permission.param.PermissionSaveParam;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.param.LongParam;
import com.jwzhu.platform.plugs.web.permission.PermissionType;

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
    @ControllerHandler(permissionType = PermissionType.SupperAdmin)
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
    @ControllerHandler(permissionType = PermissionType.Only_Login)
    public Map<Short, String> getAddAdminType(){
        return adminManager.getAddAdminType();
    }

    @PostMapping("addAdminRole")
    @ResponseBody
    @ControllerHandler
    public String addAdminRole(AdminRoleSaveParam param){
        adminManager.addAdminRole(param.initBean());
        return "添加角色成功";
    }

    @PostMapping("removeAdminRole")
    @ResponseBody
    @ControllerHandler
    public String removeAdminRole(AdminRoleSaveParam param){
        adminManager.removeAdminRole(param.initBean());
        return "取消角色成功";
    }

    @PostMapping("disable")
    @ResponseBody
    @ControllerHandler
    public String disable(LongParam param){
        adminManager.disable(param.initBean());
        return "禁用管理员成功";
    }

    @PostMapping("enable")
    @ResponseBody
    @ControllerHandler
    public String enable(LongParam param){
        adminManager.enable(param.initBean());
        return "启用管理员成功";
    }

    @PostMapping("delete")
    @ResponseBody
    @ControllerHandler
    public String delete(LongParam param){
        adminManager.delete(param.initBean());
        return "删除管理员成功";
    }

}
