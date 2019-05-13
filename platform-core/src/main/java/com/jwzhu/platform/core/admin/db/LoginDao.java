package com.jwzhu.platform.core.admin.db;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.core.admin.bean.LoginBean;
import com.jwzhu.platform.core.admin.model.Login;

@Repository
public interface LoginDao {

    void insert(LoginBean bean);

    Login getByUsername(String username);

}
