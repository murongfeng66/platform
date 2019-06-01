package com.jwzhu.platform.file.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.file.bean.FolderBean;
import com.jwzhu.platform.file.bean.FolderListBean;
import com.jwzhu.platform.file.bean.FolderUpdateBean;
import com.jwzhu.platform.file.model.Folder;
import com.jwzhu.platform.file.service.FolderService;

@Service
public class FolderManager {

    @Autowired
    private FolderService folderService;

    @Transactional
    public void insert(FolderBean bean){
        folderService.insert(bean);
    }

    public void updateById(FolderUpdateBean bean){
        folderService.updateById(bean);
    }

    public List<Folder> queryByParam(FolderListBean bean){
        return folderService.queryByParam(bean);
    }

    public Folder getById(LongBean bean){
        Folder folder = folderService.getById(bean.getId());
        if(folder == null){
            throw new BusinessException("无此文件夹");
        }
        return folder;
    }

    public void enable(LongBean bean){
        folderService.enable(bean);
    }

    public void disable(LongBean bean){
        folderService.disable(bean);
    }

    public void delete(LongBean bean){
        folderService.delete(bean);
    }

}
