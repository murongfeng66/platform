package com.jwzhu.platform.file.bean;

import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.file.model.File;

public class FileListBean extends PageBean<File> {

    private Long regionId;
    private Long folderId;
    private String key;
    private Short fileType;
    private Short permissionType;
    private Short fileStatus;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public Short getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Short fileStatus) {
        this.fileStatus = fileStatus;
    }
}
