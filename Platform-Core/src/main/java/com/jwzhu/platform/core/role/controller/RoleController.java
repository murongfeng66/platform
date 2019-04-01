package com.jwzhu.platform.core.role.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.core.role.bean.RoleListBean;
import com.jwzhu.platform.core.role.manager.RoleManager;
import com.jwzhu.platform.core.role.param.RoleListParam;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleManager roleManager;

    @ControllerHandler
    @RequestMapping({"page"})
    public ModelAndView login(ModelAndView view) {
        view.setViewName("role/role");
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

}
