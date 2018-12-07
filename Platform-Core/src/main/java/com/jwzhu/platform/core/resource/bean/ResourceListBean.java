package com.jwzhu.platform.core.resource.bean;

import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.core.resource.model.ResourceList;

public class ResourceListBean extends PageBean<ResourceList> {

    private String code;
    private String parentCode;
    private String name;
    private Integer sort;
    private Short type;
    private Short isShow;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
}
