package com.jwzhu.platform.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.common.PlatformConfig;
import com.jwzhu.platform.web.base.param.ControllerHandler;
import com.jwzhu.platform.web.base.request.RequestBaseParam;
import com.jwzhu.platform.web.base.token.TokenService;
import com.jwzhu.platform.web.base.token.TokenSubject;
import com.jwzhu.platform.core.resource.manager.ResourceManager;
import com.jwzhu.platform.core.resource.model.Menu;
import com.jwzhu.platform.core.user.manager.UserManager;

@Controller
public class CommonController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private ResourceManager resourceManager;
    @Autowired
    private PlatformConfig platformConfig;
    @Autowired
    private UserManager userManager;

    @ControllerHandler
    @RequestMapping({"", "login"})
    public ModelAndView login(ModelAndView view) {
        view.setViewName("login/login");
        return view;
    }

    @ControllerHandler
    @RequestMapping("main/{token}")
    public ModelAndView main(@PathVariable("token") String token, ModelAndView view) {
        TokenSubject subject = tokenService.checkToken(token);
        view.setViewName("main");
        view.addObject("token", token);
        view.addObject("user", userManager.getById(subject.getUserId()));
        view.addObject("platformName", platformConfig.getName());
        return view;
    }

    @ControllerHandler
    @RequestMapping("queryMenu")
    @ResponseBody
    public List<Menu> queryMenu(){
        return resourceManager.queryMenu(RequestBaseParam.getRequestUser().getUserId());
    }

}
