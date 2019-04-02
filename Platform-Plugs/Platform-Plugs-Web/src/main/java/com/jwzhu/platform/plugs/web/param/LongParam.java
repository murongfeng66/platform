package com.jwzhu.platform.plugs.web.param;

import javax.validation.constraints.NotNull;

import com.jwzhu.platform.common.bean.LongBean;

public class LongParam extends BaseParam<LongBean> {

    @NotNull(message = "参数不能为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    protected LongBean getBean() {
        return new LongBean();
    }
}
