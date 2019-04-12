package com.jwzhu.platform.core.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.core.service.bean.ServiceListBean;
import com.jwzhu.platform.core.service.manager.ServiceManager;
import com.jwzhu.platform.core.service.model.Service;
import com.jwzhu.platform.core.service.param.ServiceAddParam;
import com.jwzhu.platform.core.service.param.ServiceListParam;
import com.jwzhu.platform.core.service.param.ServiceUpdateParam;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.param.LongParam;

@Controller
@RequestMapping("service")
public class ServiceController {
    
    @Autowired
    private ServiceManager serviceManager;
    
    @RequestMapping("page")
    @ControllerHandler
    public ModelAndView login(ModelAndView view) {
        view.setViewName("service/service");
        return view;
    }

    @PostMapping("add")
    @ResponseBody
    @ControllerHandler
    public String add(ServiceAddParam param){
        serviceManager.insert(param.initBean());
        return "服务添加成功";
    }

    @GetMapping("queryByParam")
    @ResponseBody
    @ControllerHandler
    public ServiceListBean queryByParam(ServiceListParam param){
        ServiceListBean bean = param.initBean();
        serviceManager.queryByParam(bean);
        return bean;
    }

    @GetMapping("getById")
    @ResponseBody
    @ControllerHandler
    public Service getById(LongParam param){
        return serviceManager.getById(param.initBean());
    }

    @PostMapping("updateById")
    @ResponseBody
    @ControllerHandler
    public String updateById(ServiceUpdateParam param){
        serviceManager.updateById(param.initBean());
        return "服务修改成功";
    }
    
}
