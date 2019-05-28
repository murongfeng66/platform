package com.jwzhu.platform.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.file.bean.RegionListBean;
import com.jwzhu.platform.file.manager.RegionManager;
import com.jwzhu.platform.file.param.RegionAddParam;
import com.jwzhu.platform.file.param.RegionListParam;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;

@Controller
@RequestMapping("region")
public class RegionController {

    @Autowired
    private RegionManager regionManager;

    @GetMapping("page")
    @ControllerHandler
    public ModelAndView page(ModelAndView view){
        view.setViewName("region");
        return view;
    }

    @GetMapping("queryByParam")
    @ResponseBody
    @ControllerHandler
    public RegionListBean queryByParam(RegionListParam param) {
        RegionListBean bean = param.initBean();
        regionManager.queryByParam(bean);
        return bean;
    }

    @PostMapping("add")
    @ResponseBody
    @ControllerHandler
    public String add(RegionAddParam param) {
        regionManager.insert(param.initBean());
        return "添加文件域成功";
    }

}
