package com.jwzhu.platform.core.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jwzhu.platform.common.web.param.ControllerHandler;
import com.jwzhu.platform.common.web.response.PageResult;
import com.jwzhu.platform.core.resource.manager.ResourceManager;
import com.jwzhu.platform.core.resource.bean.ResourceListBean;
import com.jwzhu.platform.core.resource.model.ResourceList;
import com.jwzhu.platform.core.resource.param.ResourceListParam;
import com.jwzhu.platform.core.resource.param.ResourceParam;

@Controller
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    private ResourceManager resourceManager;

    @PostMapping("add")
    @ResponseBody
    @ControllerHandler
    public String add(ResourceParam param){
        resourceManager.insert(param.initBean());
        return "资源添加成功";
    }

    @GetMapping("queryByParam")
    @ResponseBody
    @ControllerHandler
    public PageResult<ResourceList> queryByParam(ResourceListParam param){
        ResourceListBean bean = param.initBean();
        resourceManager.queryByParam(bean);
        return bean.getResult();
    }

}
