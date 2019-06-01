package com.jwzhu.platform.file.bean;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jwzhu.platform.common.bean.BaseBean;

public class FileUploadBean extends BaseBean {

    private String folderCode;
    private List<MultipartFile> files;

    public String getFolderCode() {
        return folderCode;
    }

    public void setFolderCode(String folderCode) {
        this.folderCode = folderCode;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
