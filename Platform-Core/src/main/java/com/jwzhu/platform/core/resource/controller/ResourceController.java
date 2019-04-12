package com.jwzhu.platform.core.resource.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.core.resource.bean.ResourceListBean;
import com.jwzhu.platform.core.resource.manager.ResourceManager;
import com.jwzhu.platform.core.resource.model.Resource;
import com.jwzhu.platform.core.resource.model.ResourceType;
import com.jwzhu.platform.core.resource.param.ResourceListParam;
import com.jwzhu.platform.core.resource.param.ResourceAddParam;
import com.jwzhu.platform.core.resource.param.ResourceUpdateParam;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.param.LongParam;

@Controller
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    private ResourceManager resourceManager;

    @RequestMapping("page")
    @ControllerHandler
    public ModelAndView login(ModelAndView view) {
        view.setViewName("resource/resource");
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
    @ControllerHandler
    public Map<Short, String> resourceType(){
        return ResourceType.map;
    }

}