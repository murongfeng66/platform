package com.jwzhu.platform.core.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jwzhu.platform.common.web.RequestBaseParam;
import com.jwzhu.platform.core.admin.manager.LoginManager;
import com.jwzhu.platform.core.admin.param.LoginParam;
import com.jwzhu.platform.core.permission.manager.ResourceManager;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.permission.PermissionType;
import com.jwzhu.platform.plugs.web.response.WebResult;

@RequestMapping("login")
@Controller
public class LoginController {

    @Autowired
    private LoginManager loginManager;
    @Autowired
    private ResourceManager resourceManager;

    @PostMapping("login")
    @ResponseBody
    @ControllerHandler(permissionType = PermissionType.No)
    public WebResult<List<String>> login(LoginParam param) {
        loginManager.login(param.initBean());
        WebResult<List<String>> result = new WebResult<>();
        result.setData(resourceManager.queryAdminResourceUrl(RequestBaseParam.getRequestUser().getId()));
        result.setRedirect("/main");
        return result;
    }

    @GetMapping("logout")
    @ResponseBody
    @ControllerHandler(clearToken = true, permissionType = PermissionType.Only_Login)
    public WebResult<String> logout(String token) {
        loginManager.logout(token);
        WebResult<String> result = new WebResult<>("退出成功");
        result.setRedirect("/login");
        return result;
    }

}
