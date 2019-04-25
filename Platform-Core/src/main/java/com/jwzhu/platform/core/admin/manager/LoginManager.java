package com.jwzhu.platform.core.admin.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.admin.bean.LoginBean;
import com.jwzhu.platform.core.admin.model.Admin;
import com.jwzhu.platform.core.admin.model.Login;
import com.jwzhu.platform.core.admin.service.AdminService;
import com.jwzhu.platform.core.admin.service.LoginService;
import com.jwzhu.platform.plugs.web.token.TokenService;
import com.jwzhu.platform.plugs.web.token.TokenSubject;

@Service
public class LoginManager {
    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private TokenService tokenService;

    public String login(LoginBean bean) {
        Login login = loginService.getByUsername(bean.getUsername());
        if (login == null) {
            throw new BusinessException("用户名或密码错误");
        }
        String encryptPassword = loginService.encryptPassword(bean.getPassword(), login.getSalt());
        if (encryptPassword.equals(login.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        Admin admin = getById(login.getId());
        if (admin.getAdminStatus() != AvailableStatus.Enable.getCode()) {
            throw new BusinessException("用户被锁定");
        }
        TokenSubject subject = new TokenSubject();
        subject.setId(admin.getId());
        subject.setsId(admin.getServiceId());
        subject.setType(admin.getAdminType());
        return tokenService.createToken(subject);
    }

    public Admin getById(long id) {
        Admin admin = adminService.getById(id);
        if(admin == null){
            throw new BusinessException("管理员不存在");
        }
        return admin;
    }

    public void logout(String token) {
        tokenService.inValidToken(token);
    }

}
