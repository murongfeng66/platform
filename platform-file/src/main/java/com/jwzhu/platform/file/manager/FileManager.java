package com.jwzhu.platform.file.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.file.bean.FileListBean;
import com.jwzhu.platform.file.bean.FileUploadBean;
import com.jwzhu.platform.file.model.File;
import com.jwzhu.platform.file.model.Folder;
import com.jwzhu.platform.file.service.FileService;
import com.jwzhu.platform.file.service.FolderService;

@Service
public class FileManager {

    @Autowired
    private FileService fileService;
    @Autowired
    private FolderService folderService;

    public List<File> queryByParam(FileListBean bean) {
        return fileService.queryByParam(bean);
    }

    public void enable(LongBean bean) {
        fileService.enable(bean);
    }

    public void disable(LongBean bean) {
        fileService.disable(bean);
    }

    public void delete(LongBean bean) {
        fileService.delete(bean);
    }

    public Map<String, String> upload(FileUploadBean bean) {
        Folder folder = folderService.getByCode(bean.getFolderCode());
        if (folder == null) {
            throw new BusinessException("文件夹不存在");
        }
        if (folder.getFolderStatus() != AvailableStatus.Enable.getCode()) {
            throw new BusinessException("文件夹不可用");
        }
        Map<String, String> map = new HashMap<>();
        for (MultipartFile file : bean.getFiles()) {
            map.put(file.getOriginalFilename(), fileService.upload(folder, file));
        }
        return map;
    }

}
