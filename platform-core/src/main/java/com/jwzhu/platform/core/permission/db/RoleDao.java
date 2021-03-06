package com.jwzhu.platform.core.permission.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.core.permission.bean.GetMyRoleBean;
import com.jwzhu.platform.core.permission.bean.PermissionSaveBean;
import com.jwzhu.platform.core.permission.bean.RoleBean;
import com.jwzhu.platform.core.permission.bean.RoleListBean;
import com.jwzhu.platform.core.permission.model.AdminRole;
import com.jwzhu.platform.core.permission.model.Role;

@Repository
public interface RoleDao {

    void insert(RoleBean bean);

    List<Role> queryByParam(RoleListBean bean);

    Role getById(long id);

    int updateById(RoleBean bean);

    int updateStatus(UpdateStatusBean bean);

    /**
     * for update
     */
    Role getByCode(@Param("roleCode") String roleCode);

    void insertRoleResource(PermissionSaveBean bean);

    int deleteRoleResource(PermissionSaveBean bean);

    List<AdminRole> getAdminRole(GetMyRoleBean bean);

    List<String> getAllRoleByAdminId(long adminId);
}
