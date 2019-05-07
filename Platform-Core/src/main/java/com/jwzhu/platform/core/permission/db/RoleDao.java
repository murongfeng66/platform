package com.jwzhu.platform.core.permission.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.core.permission.bean.AdminRoleBean;
import com.jwzhu.platform.core.permission.bean.GetRoleResourceBean;
import com.jwzhu.platform.core.permission.bean.PermissionSaveBean;
import com.jwzhu.platform.core.permission.bean.RoleBean;
import com.jwzhu.platform.core.permission.bean.RoleListBean;
import com.jwzhu.platform.core.permission.model.Resource;
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

    void batchInsertRoleResource(PermissionSaveBean bean);

    int deleteRoleResource(PermissionSaveBean bean);

    List<Resource> getRoleResource(GetRoleResourceBean bean);

    void insertAdminRole(AdminRoleBean bean);

    int deleteAdminRole(AdminRoleBean bean);

    List<Role> getHaveRole(@Param("adminId") long adminId, @Param("roleStatus") short roleStatus);

    List<Resource> getHaveResource(@Param("adminId") long adminId, @Param("resourceStatus") short resourceStatus);
}
