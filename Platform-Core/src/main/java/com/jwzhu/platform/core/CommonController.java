package com.jwzhu.platform.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.common.PlatformConfig;
import com.jwzhu.platform.core.resource.manager.ResourceManager;
import com.jwzhu.platform.core.resource.model.Menu;
import com.jwzhu.platform.core.user.manager.UserManager;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;

@Controller
public class CommonController {

    @Autowired
    private ResourceManager resourceManager;
    @Autowired
    private PlatformConfig platformConfig;
    @Autowired
    private UserManager userManager;

    @ControllerHandler(needToken = false)
    @GetMapping({"", "login"})
    public ModelAndView login(ModelAndView view) {
        view.setViewName("login/login");
        view.addObject("platformName", platformConfig.getName());
        return view;
    }

    @ControllerHandler
    @RequestMapping("main")
    public ModelAndView main(ModelAndView view) {
        view.setViewName("main");
        view.addObject("platformName", platformConfig.getName());
        view.addObject("admin", userManager.getById(RequestBaseParam.getRequestUser().getUserId()));
        return view;
    }

    @ControllerHandler
    @GetMapping("queryMenu")
    @ResponseBody
    public List<Menu> queryMenu(){
        return resourceManager.queryMenu(RequestBaseParam.getRequestUser().getUserId());
    }

}
