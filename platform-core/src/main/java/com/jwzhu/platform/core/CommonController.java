package com.jwzhu.platform.core;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.common.PlatformConfig;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.enums.YesOrNo;
import com.jwzhu.platform.core.admin.manager.LoginManager;
import com.jwzhu.platform.core.permission.manager.ResourceManager;
import com.jwzhu.platform.core.permission.model.Menu;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.permission.PermissionType;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;

@Controller
public class CommonController {

    @Autowired
    private ResourceManager resourceManager;
    @Autowired
    private PlatformConfig platformConfig;
    @Autowired
    private LoginManager userManager;

    @ControllerHandler(needToken = false, permissionType = PermissionType.No)
    @GetMapping({"", "login"})
    public ModelAndView login(ModelAndView view) {
        view.setViewName("login/login");
        view.addObject("platformName", platformConfig.getName());
        return view;
    }

    @ControllerHandler(permissionType = PermissionType.Only_Login)
    @RequestMapping("main")
    public ModelAndView main(ModelAndView view) {
        view.setViewName("main");
        view.addObject("platformName", platformConfig.getName());
        view.addObject("admin", userManager.getById(RequestBaseParam.getRequestUser().getId()));
        return view;
    }

    @GetMapping("queryMenu")
    @ResponseBody
    @ControllerHandler(permissionType = PermissionType.Only_Login)
    public List<Menu> queryMenu(){
        return resourceManager.queryMenu();
    }

    @GetMapping("yesOrNo")
    @ResponseBody
    @ControllerHandler(permissionType = PermissionType.Only_Login)
    public Map<Short, String> yesOrNo(){
        return YesOrNo.map;
    }

    @GetMapping("availableStatus")
    @ResponseBody
    @ControllerHandler(permissionType = PermissionType.Only_Login)
    public Map<Short, String> availableStatus(){
        return AvailableStatus.map;
    }

}
