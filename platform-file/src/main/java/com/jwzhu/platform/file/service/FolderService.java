package com.jwzhu.platform.file.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.util.StringUtil;
import com.jwzhu.platform.common.web.RequestBaseParam;
import com.jwzhu.platform.file.db.FolderDao;
import com.jwzhu.platform.file.bean.FolderBean;
import com.jwzhu.platform.file.bean.FolderListBean;
import com.jwzhu.platform.file.bean.FolderUpdateBean;
import com.jwzhu.platform.file.model.Folder;

@Service
public class FolderService {

    @Autowired
    private FolderDao folderDao;

    public void insert(FolderBean bean) {
        if (folderDao.getByPathWithLock(bean) != null) {
            throw new BusinessException("路径已存在");
        }
        bean.setFolderCode(bean.getFolderCode() == null ? StringUtil.createCode("D" ): bean.getFolderCode());
        bean.setCreateTime(bean.getCreateTime() == null ? RequestBaseParam.getRequestTime() : bean.getCreateTime());
        bean.setFolderStatus(bean.getFolderStatus() == null ? AvailableStatus.Enable.getCode() : bean.getFolderStatus());
        folderDao.insert(bean);
    }

    public void updateById(FolderUpdateBean bean) {
        bean.setUpdateTime(bean.getUpdateTime() == null ? RequestBaseParam.getRequestTime() : bean.getUpdateTime());
        if (folderDao.updateById(bean) == 0) {
            throw new BusinessException("修改失败");
        }
    }

    public List<Folder> queryByParam(FolderListBean bean) {
        return folderDao.queryByParam(bean);
    }

    public Folder getById(long id) {
        return folderDao.getById(id);
    }

    private void updateStatus(UpdateStatusBean bean, String errorMessage) {
        bean.setUpdateTime(RequestBaseParam.getRequestTime());
        if (folderDao.updateStatus(bean) == 0) {
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

    public Folder getByCode(String folderCode){
        return folderDao.getByCode(folderCode);
    }

}
