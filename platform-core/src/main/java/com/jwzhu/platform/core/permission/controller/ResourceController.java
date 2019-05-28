package com.jwzhu.platform.core.permission.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.core.permission.bean.ResourceListBean;
import com.jwzhu.platform.core.permission.manager.ResourceManager;
import com.jwzhu.platform.core.permission.model.Resource;
import com.jwzhu.platform.core.permission.model.ResourceType;
import com.jwzhu.platform.core.permission.param.ResourceAddParam;
import com.jwzhu.platform.core.permission.param.ResourceListParam;
import com.jwzhu.platform.core.permission.param.ResourceUpdateParam;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.param.LongParam;
import com.jwzhu.platform.plugs.web.permission.PermissionType;

@Controller
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    private ResourceManager resourceManager;

    @RequestMapping("page")
    @ControllerHandler
    public ModelAndView login(ModelAndView view) {
        view.setViewName("permission/resource");
        return view;
    }

    @PostMapping("add")
    @ResponseBody
    @ControllerHandler
    public String add(ResourceAddParam param){
        resourceManager.insert(param.initBean());
        return "资源添加成功";
    }

    @GetMapping("queryByParam")
    @ResponseBody
    @ControllerHandler
    public ResourceListBean queryByParam(ResourceListParam param){
        ResourceListBean bean = param.initBean();
        resourceManager.queryByParam(bean);
        return bean;
    }

    @GetMapping("getById")
    @ResponseBody
    @ControllerHandler
    public Resource getById(LongParam param){
        return resourceManager.getById(param.initBean());
    }

    @PostMapping("updateById")
    @ResponseBody
    @ControllerHandler
    public String updateById(ResourceUpdateParam param){
        resourceManager.updateById(param.initBean());
        return "资源修改成功";
    }

    @GetMapping("resourceType")
    @ResponseBody
    @ControllerHandler(permissionType = PermissionType.Only_Login)
    public Map<Short, String> resourceType(){
        return ResourceType.map;
    }

    @PostMapping("disable")
    @ResponseBody
    @ControllerHandler
    public String disable(LongParam param){
        resourceManager.disable(param.initBean());
        return "禁用资源成功";
    }

    @PostMapping("enable")
    @ResponseBody
    @ControllerHandler
    public String enable(LongParam param){
        resourceManager.enable(param.initBean());
        return "启用资源成功";
    }

    @PostMapping("delete")
    @ResponseBody
    @ControllerHandler
    public String delete(LongParam param){
        resourceManager.delete(param.initBean());
        return "删除资源成功";
    }

}
