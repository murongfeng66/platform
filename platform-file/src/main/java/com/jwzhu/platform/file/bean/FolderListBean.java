package com.jwzhu.platform.file.bean;

import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.file.model.Folder;

public class FolderListBean extends PageBean<Folder> {

    private String key;
    private Long regionId;
    private Short folderStatus;
    private Short permissionType;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
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
}
