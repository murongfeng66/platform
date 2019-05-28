package com.jwzhu.platform.plugs.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.jwzhu.platform.common.SystemConfig;
import com.jwzhu.platform.common.web.RequestBaseParam;
import com.jwzhu.platform.common.web.TokenSubject;
import com.jwzhu.platform.permission.PermissionService;
import com.jwzhu.platform.plugs.cache.base.CacheUtil;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.permission.PermissionType;
import com.jwzhu.platform.plugs.web.token.TokenService;

@Controller
public class WebCommonController {

    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SystemConfig systemConfig;

    @GetMapping("setTokenToCookie")
    public ModelAndView setTokenToCookie(ModelAndView view, HttpServletRequest request) {
        String originUrl = request.getParameter("originUrl");
        String token = request.getParameter(tokenService.getTokenConfig().getParamName());
        view.addObject("originUrl", originUrl);
        view.addObject("token", token);
        view.setViewName("setTokenToCookie");
        return view;
    }

    @PostMapping("subInitLoginInfo")
    @ResponseBody
    public String subInitLoginInfo(HttpServletRequest request) {
        String token = request.getHeader(tokenService.getTokenConfig().getParamName());
        TokenSubject subject = tokenService.analyzeToken(token);
        long tokenExpire = tokenService.getTokenConfig().getExpiredTime().toMillis() - (System.currentTimeMillis() - subject.getTime());
        cacheUtil.set(tokenService.getCacheKey(token), subject, tokenExpire);

        String resourceCodes = request.getParameter("resourceCodes");
        String codesCacheKey = permissionService.getCacheKey(subject.getId(), PermissionService.PermissionCacheType.Code);
        cacheUtil.sAdd(codesCacheKey, JSON.parseArray(resourceCodes, String.class).toArray(new String[]{}));
        cacheUtil.expired(codesCacheKey, systemConfig.getResourceTimeout().toMillis());

        String resourceUrls = request.getParameter("resourceUrls");
        String urlsCacheKey = permissionService.getCacheKey(subject.getId(), PermissionService.PermissionCacheType.Url);
        cacheUtil.sAdd(urlsCacheKey, JSON.parseArray(resourceUrls, String.class).toArray(new String[]{}));
        cacheUtil.expired(urlsCacheKey, systemConfig.getResourceTimeout().toMillis());
        return "初始化成功";
    }

    @GetMapping("getPermissionCodes")
    @ResponseBody
    @ControllerHandler(permissionType = PermissionType.Only_Login)
    public Collection<String> getPermissionList() {
        return permissionService.getPermissions(PermissionService.PermissionCacheType.Code);
    }

    @PostMapping("logout")
    @ResponseBody
    @ControllerHandler(permissionType = PermissionType.Only_Login)
    public String logout() {
        TokenSubject subject = tokenService.analyzeToken(RequestBaseParam.getRequestToken());
        cacheUtil.delete(tokenService.getCacheKey(RequestBaseParam.getRequestToken()));

        String cacheKey = permissionService.getCacheKey(subject.getId(), PermissionService.PermissionCacheType.Code);
        cacheUtil.delete(cacheKey);
        cacheKey = permissionService.getCacheKey(subject.getId(), PermissionService.PermissionCacheType.Url);
        cacheUtil.delete(cacheKey);
        return "登出成功";
    }

}
