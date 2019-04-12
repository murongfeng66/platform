package com.jwzhu.platform.core.resource.param;

import com.jwzhu.platform.core.resource.bean.ResourceListBean;
import com.jwzhu.platform.plugs.web.param.PageParam;

public class ResourceListParam extends PageParam<ResourceListBean> {

    private String key;
    private Short type;
    private Short menuShow;
    private Short availableStatus;

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

    public Short getMenuShow() {
        return menuShow;
    }

    public void setMenuShow(Short menuShow) {
        this.menuShow = menuShow;
    }

    public Short getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(Short availableStatus) {
        this.availableStatus = availableStatus;
    }

    @Override
    protected ResourceListBean getBean() {
        ResourceListBean bean = new ResourceListBean();
        bean.addSort("code");
        return bean;
    }
}
