package com.jwzhu.platform.file.bean;

import java.time.LocalDateTime;

import com.jwzhu.platform.common.bean.BaseBean;

public class FileBean extends BaseBean {

    private Long id;
    private Long regionId;
    private Long folderId;
    private String fileName;
    private Short fileType;
    private Short permissionType;
    private String permissionData;
    private Short fileStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String originName;

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

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Short getFileType() {
        return fileType;
    }

    public void setFileType(Short fileType) {
        this.fileType = fileType;
    }

    public Short getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Short permissionType) {
        this.permissionType = permissionType;
    }

    public String getPermissionData() {
        return permissionData;
    }

    public void setPermissionData(String permissionData) {
        this.permissionData = permissionData;
    }

    public Short getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Short fileStatus) {
        this.fileStatus = fileStatus;
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

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }
}
