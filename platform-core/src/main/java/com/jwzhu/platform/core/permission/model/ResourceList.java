package com.jwzhu.platform.core.permission.model;

import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.enums.YesOrNo;
import com.jwzhu.platform.plugs.jsonescape.bind.EnumEscape;

public class ResourceList {

    private Long id;
    private String code;
    private String parentCode;
    private String name;
    private Integer sort;
    @EnumEscape(ResourceType.class)
    private Short type;
    @EnumEscape(YesOrNo.class)
    private Short menuShow;
    @EnumEscape(AvailableStatus.class)
    private Short resourceStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Short getMenuShow() {
        return menuShow;
    }

    public void setMenuShow(Short menuShow) {
        this.menuShow = menuShow;
    }

    public Short getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(Short resourceStatus) {
        this.resourceStatus = resourceStatus;
    }
}
