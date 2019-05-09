package com.jwzhu.platform.core.permission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.core.permission.bean.RoleListBean;
import com.jwzhu.platform.core.permission.manager.RoleManager;
import com.jwzhu.platform.core.permission.model.Role;
import com.jwzhu.platform.core.permission.param.RoleAddParam;
import com.jwzhu.platform.core.permission.param.RoleListParam;
import com.jwzhu.platform.core.permission.param.RoleUpdateParam;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.param.LongParam;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleManager roleManager;

    @ControllerHandler
    @RequestMapping({"page"})
    public ModelAndView login(ModelAndView view) {
        view.setViewName("permission/role");
        return view;
    }

    @GetMapping("queryByParam")
    @ResponseBody
    @ControllerHandler
    public RoleListBean queryByParam(RoleListParam param){
        RoleListBean bean = param.initBean();
        roleManager.queryByParam(bean);
        return bean;
    }

    @PostMapping("add")
    @ResponseBody
    @ControllerHandler
    public String add(RoleAddParam param){
        roleManager.insert(param.initBean());
        return "角色添加成功";
    }

    @GetMapping("getById")
    @ResponseBody
    @ControllerHandler
    public Role getById(LongParam param){
        return roleManager.getById(param.getId());
    }

    @PostMapping("updateById")
    @ResponseBody
    @ControllerHandler
    public String updateById(RoleUpdateParam param){
        roleManager.updateById(param.initBean());
        return "角色修改成功";
    }

    @PostMapping("disable")
    @ResponseBody
    @ControllerHandler
    public String disable(LongParam param){
        roleManager.disable(param.initBean());
        return "禁用角色成功";
    }

    @PostMapping("enable")
    @ResponseBody
    @ControllerHandler
    public String enable(LongParam param){
        roleManager.enable(param.initBean());
        return "启用角色成功";
    }

    @PostMapping("delete")
    @ResponseBody
    @ControllerHandler
    public String delete(LongParam param){
        roleManager.delete(param.initBean());
        return "删除角色成功";
    }

}
