package com.jwzhu.platform.file.model;

import java.time.LocalDateTime;

import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.plugs.jsonescape.bind.EnumEscape;

public class Region {

    private Long id;
    private String regionName;
    private String regionCode;
    @EnumEscape(AvailableStatus.class)
    private Short regionStatus;
    private String path;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Short getRegionStatus() {
        return regionStatus;
    }

    public void setRegionStatus(Short regionStatus) {
        this.regionStatus = regionStatus;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
}
