package com.jwzhu.platform.file.model;

import java.time.LocalDateTime;

public class Folder {

    private Long id;
    private Long regionId;
    private String folderName;
    private String folderCode;
    private Short folderStatus;
    private Short permissionType;
    private String path;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderCode() {
        return folderCode;
    }

    public void setFolderCode(String folderCode) {
        this.folderCode = folderCode;
    }

    public Short getFolderStatus() {
        return folderStatus;
    }

    public void setFolderStatus(Short folderStatus) {
        this.folderStatus = folderStatus;
    }

    public Short getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Short permissionType) {
        this.permissionType = permissionType;
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
