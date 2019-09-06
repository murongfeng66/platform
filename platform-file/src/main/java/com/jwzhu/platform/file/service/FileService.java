package com.jwzhu.platform.file.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.util.StringUtil;
import com.jwzhu.platform.common.web.RequestInfo;
import com.jwzhu.platform.file.FileConfig;
import com.jwzhu.platform.file.bean.FileBean;
import com.jwzhu.platform.file.bean.FileListBean;
import com.jwzhu.platform.file.db.FileDao;
import com.jwzhu.platform.file.model.File;
import com.jwzhu.platform.file.model.Folder;

@Service
public class FileService {

    private static Logger logger = LoggerFactory.getLogger(FileService.class);
    @Autowired
    private FileDao fileDao;
    @Autowired
    private FileConfig fileConfig;

    public void insert(FileBean bean) {
        if (fileDao.getWithLock(bean) != null) {
            throw new BusinessException("存在相同文件");
        }
        bean.setCreateTime(bean.getCreateTime() == null ? RequestInfo.getRequestTime() : bean.getCreateTime());
        bean.setFileStatus(bean.getFileStatus() == null ? AvailableStatus.Enable.getCode() : bean.getFileStatus());
        fileDao.insert(bean);
    }

    public List<File> queryByParam(FileListBean bean) {
        return fileDao.queryByParam(bean);
    }

    private void updateStatus(UpdateStatusBean bean, String errorMessage) {
        bean.setUpdateTime(bean.getUpdateTime() == null ? RequestInfo.getRequestTime() : bean.getUpdateTime());
        if (fileDao.updateStatus(bean) == 0) {
            throw new BusinessException(errorMessage);
        }
    }

    public void enable(LongBean bean) {
        updateStatus(new UpdateStatusBean(bean.getId(), AvailableStatus.Disable.getCode(), AvailableStatus.Enable.getCode()), "启用失败");
    }

    public void disable(LongBean bean) {
        updateStatus(new UpdateStatusBean(bean.getId(), AvailableStatus.Enable.getCode(), AvailableStatus.Disable.getCode()), "禁用失败");
    }

    public void delete(LongBean bean) {
        updateStatus(new UpdateStatusBean(bean.getId(), AvailableStatus.Disable.getCode(), AvailableStatus.Delete.getCode()), "删除失败");
    }

    @Transactional
    public String upload(Folder folder, MultipartFile file) {
        logger.debug("上传文件：名称={}类型={}大小={}", file.getOriginalFilename(), file.getContentType(), file.getSize());
        if (StringUtil.isEmpty(file.getOriginalFilename())) {
            throw new BusinessException("文件名为空");
        }
        String[] originNames = file.getOriginalFilename().split("\\.");
        String targetFolderPath = folder.getPath().endsWith("/") ? folder.getPath() : folder.getPath() + "/";
        String targetFolderAllPath = fileConfig.getRootPath() + targetFolderPath;

        java.io.File targetFolderFile = new java.io.File(targetFolderAllPath);
        if (!targetFolderFile.exists()) {
            if (!targetFolderFile.mkdirs()) {
                throw new SystemException("新建文件夹失败");
            }
        }

        String targetFileName = StringUtil.createCode("") + "." + originNames[1];
        logger.debug("目标文件：名称={}路径={}", targetFileName, targetFolderAllPath);

        FileBean fileBean = new FileBean();
        fileBean.setFileName(targetFileName);
        fileBean.setFolderId(folder.getId());
        fileBean.setOriginName(file.getOriginalFilename());
        fileBean.setPermissionType(folder.getPermissionType());

        java.io.File targetFile = new java.io.File(targetFolderAllPath + targetFileName);

        try {
            insert(fileBean);
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new SystemException("写入文件失败", e);
        }

        return targetFolderPath + targetFileName;
    }

}
