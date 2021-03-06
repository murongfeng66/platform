package com.jwzhu.platform.core.admin.param;

import javax.validation.constraints.NotEmpty;

import com.jwzhu.platform.core.admin.bean.LoginBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class LoginParam extends BaseParam<LoginBean> {

    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected LoginBean getBean() {
        return new LoginBean();
    }
}
