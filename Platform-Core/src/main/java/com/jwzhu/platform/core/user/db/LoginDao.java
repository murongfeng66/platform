package com.jwzhu.platform.core.user.db;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.core.user.bean.LoginBean;
import com.jwzhu.platform.core.user.model.Login;

@Repository
public interface LoginDao {

    void insert(LoginBean bean);

    Login getByUsername(String username);

}
