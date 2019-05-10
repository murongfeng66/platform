package com.jwzhu.platform.core.admin.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.core.admin.bean.AdminBean;
import com.jwzhu.platform.core.admin.bean.AdminListBean;
import com.jwzhu.platform.core.admin.model.Admin;
import com.jwzhu.platform.core.permission.bean.AdminRoleBean;

@Repository
public interface AdminDao {

    Admin getById(long id);

    List<Admin> queryByParam(AdminListBean bean);

    void insert(AdminBean bean);

    int updateById(AdminBean bean);

    void insertAdminRole(AdminRoleBean bean);

    int deleteAdminRole(AdminRoleBean bean);

    int updateStatus(UpdateStatusBean bean);

}
