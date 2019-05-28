package com.jwzhu.platform.file.param;

import com.jwzhu.platform.file.bean.RegionListBean;
import com.jwzhu.platform.plugs.web.param.PageParam;

public class RegionListParam extends PageParam<RegionListBean> {

    private String key;
    private Short regionStatus;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Short getRegionStatus() {
        return regionStatus;
    }

    public void setRegionStatus(Short regionStatus) {
        this.regionStatus = regionStatus;
    }

    @Override
    protected RegionListBean getBean() {
        return new RegionListBean();
    }
}
