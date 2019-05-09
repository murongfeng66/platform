package com.jwzhu.platform.core.permission.model;

import java.util.List;

public class ResourcePermission {

    private String code;
    private String parentCode;
    private String name;
    private Boolean have;
    private Short type;
    private List<ResourcePermission> children;

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

    public Boolean getHave() {
        return have;
    }

    public void setHave(Boolean have) {
        this.have = have;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public List<ResourcePermission> getChildren() {
        return children;
    }

    public void setChildren(List<ResourcePermission> children) {
        this.children = children;
    }
}
