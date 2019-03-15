package com.jwzhu.platform.core.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jwzhu.platform.core.user.manager.UserManager;
import com.jwzhu.platform.core.user.param.LoginParam;
import com.jwzhu.platform.plugs.web.param.ControllerHandler;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;

@RequestMapping("login")
@Controller
public class LoginController {

    @Autowired
    private UserManager userManager;

    @ControllerHandler(needToken = false)
    @PostMapping("login")
    @ResponseBody
    public String login(LoginParam param) {
        String token = userManager.login(param.initBean());
        RequestBaseParam.setRefreshToken(token);
        return "登录成功";
    }

    @ControllerHandler(clearToken = true)
    @GetMapping("logout")
    @ResponseBody
    public void logout(String token) {
        userManager.logout(token);
    }

}
