package com.jwzhu.platform.core.admin.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.admin.bean.LoginBean;
import com.jwzhu.platform.core.admin.db.LoginDao;
import com.jwzhu.platform.core.admin.model.Login;

@Service
public class LoginService {

    @Autowired
    private LoginDao loginDao;

    public void insert(LoginBean bean) {
        if (loginDao.getByUsername(bean.getUsername()) != null) {
            throw new BusinessException("账号已存在");
        }
        bean.setCreateTime(bean.getCreateTime() == null ? LocalDateTime.now() : bean.getCreateTime());
        bean.setStatus(bean.getStatus() == null ? AvailableStatus.Enable.getCode() : bean.getStatus());
        bean.setSalt(createSalt());
        bean.setPassword(encryptPassword(bean.getPassword(),bean.getSalt()));
        loginDao.insert(bean);
    }

    public Login getByUsername(String username) {
        return loginDao.getByUsername(username);
    }

    public String encryptPassword(String password,String salt) {
        return DigestUtils.md5Hex(password.concat(salt));
    }

    private String createSalt() {
        int bound = 100000000;
        int number_10 = new SecureRandom().nextInt(bound) + bound;
        return Integer.toString(number_10, 32);
    }

}
