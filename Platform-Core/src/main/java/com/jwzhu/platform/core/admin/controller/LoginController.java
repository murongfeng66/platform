package com.jwzhu.platform.core.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jwzhu.platform.core.admin.manager.LoginManager;
import com.jwzhu.platform.core.admin.param.LoginParam;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;
import com.jwzhu.platform.plugs.web.response.WebResult;

@RequestMapping("login")
@Controller
public class LoginController {

    @Autowired
    private LoginManager loginManager;

    @PostMapping("login")
    @ResponseBody
    @ControllerHandler(needToken = false)
    public WebResult<String> login(LoginParam param) {
        String token = loginManager.login(param.initBean());
        RequestBaseParam.setRefreshToken(token);
        WebResult<String> result = new WebResult<>("登录成功");
        result.setRedirect("/main");
        return result;
    }

    @GetMapping("logout")
    @ResponseBody
    @ControllerHandler(clearToken = true)
    public WebResult<String> logout(String token) {
        loginManager.logout(token);
        WebResult<String> result = new WebResult<>("退出成功");
        result.setRedirect("/login");
        return result;
    }

}
