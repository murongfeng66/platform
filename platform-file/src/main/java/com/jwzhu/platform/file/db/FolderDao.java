package com.jwzhu.platform.file.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.file.bean.FolderBean;
import com.jwzhu.platform.file.bean.FolderListBean;
import com.jwzhu.platform.file.bean.FolderUpdateBean;
import com.jwzhu.platform.file.model.Folder;

@Repository
public interface FolderDao {

    void insert(FolderBean bean);

    int updateById(FolderUpdateBean bean);

    List<Folder> queryByParam(FolderListBean bean);

    Folder getById(long id);

    String getByPathWithLock(FolderBean path);

    int updateStatus(UpdateStatusBean bean);

    Folder getByCode(String folderCode);

}
