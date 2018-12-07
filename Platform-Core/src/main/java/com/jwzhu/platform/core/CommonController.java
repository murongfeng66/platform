package com.jwzhu.platform.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.common.web.param.ControllerHandler;
import com.jwzhu.platform.common.web.request.RequestBaseParam;
import com.jwzhu.platform.common.web.token.TokenService;
import com.jwzhu.platform.core.resource.manager.ResourceManager;
import com.jwzhu.platform.core.resource.model.Menu;

@Controller
public class CommonController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private ResourceManager resourceManager;

    @ControllerHandler
    @RequestMapping({"/", "/login"})
    public ModelAndView login(ModelAndView view) {
        view.setViewName("login/login");
        return view;
    }

    @ControllerHandler
    @RequestMapping({"/main/{token}"})
    public ModelAndView mainWithToken(@PathVariable("token") String token, ModelAndView view) {
        tokenService.checkToken(token);
        view.setViewName("main");
        view.addObject("token", token);
        return view;
    }

    @ControllerHandler
    @RequestMapping({"/queryMenu"})
    @ResponseBody
    public List<Menu> queryMenu(){
        return resourceManager.queryMenu(RequestBaseParam.getRequestUser().getUserId());
    }

}
