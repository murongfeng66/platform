package com.jwzhu.platform.core.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.common.web.param.ControllerHandler;
import com.jwzhu.platform.core.user.manager.UserManager;
import com.jwzhu.platform.core.user.param.LoginParam;

@RequestMapping("login")
@Controller
public class LoginController {

    @Autowired
    private UserManager userManager;

    @ControllerHandler
    @PostMapping("login")
    @ResponseBody
    public String login(LoginParam param){
        return userManager.login(param.initBean());
    }

}
