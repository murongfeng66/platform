package com.jwzhu.platform.file.bean;

import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.file.model.Region;

public class RegionListBean extends PageBean<Region> {

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
}
