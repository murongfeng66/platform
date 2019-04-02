package com.jwzhu.platform.core.user.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.core.user.model.UserType;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.user.bean.LoginBean;
import com.jwzhu.platform.core.user.model.Admin;
import com.jwzhu.platform.core.user.model.Login;
import com.jwzhu.platform.core.user.service.AdminService;
import com.jwzhu.platform.core.user.service.LoginService;
import com.jwzhu.platform.plugs.web.token.TokenService;
import com.jwzhu.platform.plugs.web.token.TokenSubject;

@Service
public class UserManager {
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
        if (UserType.Admin.getCode() == login.getUserType()) {
            Admin admin = adminService.getById(login.getId());
            if(admin.getStatus() != AvailableStatus.Enable.getCode()){
                throw new BusinessException("用户被锁定");
            }
        }else{
            throw new BusinessException("不支持的用户类型");
        }
        return tokenService.createToken(new TokenSubject(login.getUserId(), login.getUserType()));
    }

    public Admin getById(long id){
        return adminService.getById(id);
    }

    public void logout(String token){
        tokenService.inValidToken(token);
    }

}
