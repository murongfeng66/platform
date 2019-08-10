package com.jwzhu.platform.plugs.web.param;

import javax.validation.constraints.NotEmpty;

import com.jwzhu.platform.common.bean.StringBean;

public class StringParam extends BaseParam<StringBean> {

    @NotEmpty(message = "参数不能为空")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    protected StringBean getBean() {
        return new StringBean();
    }
}
