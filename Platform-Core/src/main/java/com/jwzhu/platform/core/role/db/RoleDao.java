package com.jwzhu.platform.core.role.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.core.role.bean.RoleBean;
import com.jwzhu.platform.core.role.bean.RoleListBean;
import com.jwzhu.platform.core.role.model.Role;

@Repository
public interface RoleDao {

    void insert(RoleBean bean);

    List<Role> queryByParam(RoleListBean bean);

    Role getById(long id);

    int updateById(RoleBean bean);

}
