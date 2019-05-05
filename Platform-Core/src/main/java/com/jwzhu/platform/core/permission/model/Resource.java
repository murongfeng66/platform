package com.jwzhu.platform.core.permission.model;

import java.time.LocalDateTime;

import com.jwzhu.platform.common.enums.AvailableStatusEscaper;
import com.jwzhu.platform.common.enums.YesOrNoEscaper;
import com.jwzhu.platform.core.service.model.ServiceEscaper;
import com.jwzhu.platform.plugs.jsonEscape.bind.LongEscape;
import com.jwzhu.platform.plugs.jsonEscape.bind.ShortEscape;

public class Resource {

    private Long id;
    private String code;
    private String parentCode;
    private String name;
    private String url;
    private Integer sort;
    @ShortEscape(ResourceTypeEscaper.class)
    private Short type;
    @ShortEscape(YesOrNoEscaper.class)
    private Short menuShow;
    @ShortEscape(AvailableStatusEscaper.class)
    private Short availableStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @LongEscape(value = ServiceEscaper.class, targetNames = {"serviceCode","serviceName"})
    private Long serviceId;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Short getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(Short availableStatus) {
        this.availableStatus = availableStatus;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}