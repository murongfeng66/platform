package com.jwzhu.platform.core.admin.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
import com.jwzhu.platform.permission.PermissionService;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.permission.PermissionType;
import com.jwzhu.platform.plugs.web.response.WebResult;
import com.jwzhu.platform.plugs.web.token.TokenService;

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
    public WebResult<Collection<String>> login(LoginParam param) {
        loginManager.login(param.initBean());
        WebResult<Collection<String>> result = new WebResult<>();
        result.setData(resourceManager.getPermissions(PermissionService.PermissionCacheType.Code));
        result.setRedirect("/main");
        return result;
    }

    @GetMapping("logout")
    @ResponseBody
    @ControllerHandler(clearToken = true, permissionType = PermissionType.No)
    public void logout(HttpServletResponse response) throws IOException {
        if (RequestBaseParam.getRequestToken() != null) {
            loginManager.logout(RequestBaseParam.getRequestToken());
        }
        response.sendRedirect("/");
    }

}
