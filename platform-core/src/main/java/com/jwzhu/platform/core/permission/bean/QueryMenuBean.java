package com.jwzhu.platform.core.permission.bean;

import com.jwzhu.platform.common.bean.BaseBean;

public class QueryMenuBean extends BaseBean {

    private Long selfId;
    private Short menuShow;
    private Short enableStatusCode;
    private Short[] types;

    public Long getSelfId() {
        return selfId;
    }

    public void setSelfId(Long selfId) {
        this.selfId = selfId;
    }

    public Short getMenuShow() {
        return menuShow;
    }

    public void setMenuShow(Short menuShow) {
        this.menuShow = menuShow;
    }

    public Short getEnableStatusCode() {
        return enableStatusCode;
    }

    public void setEnableStatusCode(Short enableStatusCode) {
        this.enableStatusCode = enableStatusCode;
    }

    public Short[] getTypes() {
        return types;
    }

    public void setTypes(Short... types) {
        this.types = types;
    }
}
