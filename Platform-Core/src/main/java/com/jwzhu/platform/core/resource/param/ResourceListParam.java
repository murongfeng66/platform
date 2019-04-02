package com.jwzhu.platform.core.resource.param;

import com.jwzhu.platform.core.resource.bean.ResourceListBean;
import com.jwzhu.platform.plugs.web.param.PageParam;

public class ResourceListParam extends PageParam<ResourceListBean> {

    private String key;
    private Short type;
    private Short isShow;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getIsShow() {
        return isShow;
    }

    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    @Override
    protected ResourceListBean getBean() {
        return new ResourceListBean();
    }
}
