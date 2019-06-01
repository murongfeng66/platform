package com.jwzhu.platform.file.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.file.bean.FileBean;
import com.jwzhu.platform.file.bean.FileListBean;
import com.jwzhu.platform.file.model.File;

@Repository
public interface FileDao {

    void insert(FileBean bean);

    String getWithLock(FileBean bean);

    List<File> queryByParam(FileListBean bean);

    int updateStatus(UpdateStatusBean bean);
}
