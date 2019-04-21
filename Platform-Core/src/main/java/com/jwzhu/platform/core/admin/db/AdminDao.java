package com.jwzhu.platform.core.admin.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.core.admin.bean.AdminBean;
import com.jwzhu.platform.core.admin.bean.AdminListBean;
import com.jwzhu.platform.core.admin.model.Admin;

@Repository
public interface AdminDao {

    Admin getById(long id);

    List<Admin> queryByParam(AdminListBean bean);

    void insert(AdminBean bean);

    int updateById(AdminBean bean);

}
