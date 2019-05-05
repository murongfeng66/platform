package com.jwzhu.platform.core.permission.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.core.permission.bean.PermissionSaveBean;
import com.jwzhu.platform.core.permission.bean.RoleBean;
import com.jwzhu.platform.core.permission.bean.RoleListBean;
import com.jwzhu.platform.core.permission.bean.RoleResourceBean;
import com.jwzhu.platform.core.permission.model.Role;

@Repository
public interface RoleDao {

    void insert(RoleBean bean);

    List<Role> queryByParam(RoleListBean bean);

    Role getById(long id);

    int updateById(RoleBean bean);

    void batchInsertRoleResource(PermissionSaveBean bean);

    int deleteRoleResource(PermissionSaveBean bean);

}
