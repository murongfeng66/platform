package com.jwzhu.platform.core.admin.manager;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.util.HttpUtil;
import com.jwzhu.platform.common.web.RequestInfo;
import com.jwzhu.platform.common.web.TokenSubject;
import com.jwzhu.platform.core.admin.bean.LoginBean;
import com.jwzhu.platform.core.admin.model.Admin;
import com.jwzhu.platform.core.admin.model.Login;
import com.jwzhu.platform.core.admin.service.AdminService;
import com.jwzhu.platform.core.admin.service.LoginService;
import com.jwzhu.platform.permission.PermissionService;
import com.jwzhu.platform.plugs.cache.base.CacheUtil;
import com.jwzhu.platform.plugs.web.response.ResponseCode;
import com.jwzhu.platform.plugs.web.response.WebResult;
import com.jwzhu.platform.plugs.web.token.TokenService;

@Service
public class LoginManager {
    private Logger logger = LoggerFactory.getLogger(LoginManager.class);
    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private PermissionService permissionService;

    public void login(LoginBean bean) {
        Login login = loginService.getByUsername(bean.getUsername());
        if (login == null) {
            throw new BusinessException("用户名或密码错误");
        }
        String encryptPassword = loginService.encryptPassword(bean.getPassword(), login.getSalt());
        if (!encryptPassword.equals(login.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        Admin admin = getById(login.getId());
        if (admin.getAdminStatus() != AvailableStatus.Enable.getCode()) {
            throw new BusinessException("用户被锁定");
        }
        TokenSubject subject = new TokenSubject();
        subject.setId(admin.getId());
        subject.setType(admin.getAdminType());
        String token = tokenService.createToken(subject);

        RequestInfo.setRequestUser(subject);

        String cacheKey = permissionService.getCacheKey(admin.getId(), PermissionService.PermissionCacheType.Url);
        cacheUtil.delete(cacheKey);
        cacheKey = permissionService.getCacheKey(admin.getId(), PermissionService.PermissionCacheType.Code);
        cacheUtil.delete(cacheKey);
    }

    public Admin getById(long id) {
        Admin admin = adminService.getById(id);
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }
        return admin;
    }

    public void logout(String token) {
        tokenService.inValidToken(token);
        String cacheKey = permissionService.getCacheKey(RequestInfo.getRequestUser().getId(), PermissionService.PermissionCacheType.Url);
        cacheUtil.delete(cacheKey);
        cacheKey = permissionService.getCacheKey(RequestInfo.getRequestUser().getId(), PermissionService.PermissionCacheType.Code);
        cacheUtil.delete(cacheKey);

        Set<String> subHosts = cacheUtil.sMembers("SubSystem:" + token);
        for (String subHost : subHosts) {
            logger.info("{}退出", subHost);
            HttpUtil.RequestBean requestBean = new HttpUtil.RequestBean();
            requestBean.addHeader("x-requested-with", "XMLHttpRequest");
            requestBean.setUrl(subHost + "/logout");
            requestBean.addParam("Token", token);
            String resultStr = HttpUtil.doPost(requestBean);
            WebResult<String> result = JSON.parseObject(resultStr, new TypeReference<WebResult<String>>() {
            });
            if (result.getCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("子系统退出失败");
            }
        }
    }

}
