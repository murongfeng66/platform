package com.jwzhu.platform.core.permission.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.core.permission.bean.GetMyResourceBean;
import com.jwzhu.platform.core.permission.bean.PermissionSaveBean;
import com.jwzhu.platform.core.permission.bean.QueryMenuBean;
import com.jwzhu.platform.core.permission.bean.ResourceBean;
import com.jwzhu.platform.core.permission.bean.ResourceListBean;
import com.jwzhu.platform.core.permission.model.Menu;
import com.jwzhu.platform.core.permission.model.Resource;
import com.jwzhu.platform.core.permission.model.ResourceList;
import com.jwzhu.platform.core.permission.model.ResourcePermission;

@Repository
public interface ResourceDao {

    void insert(ResourceBean bean);

    List<ResourceList> queryByParam(ResourceListBean bean);

    List<Menu> queryMenu(QueryMenuBean bean);

    int updateById(ResourceBean bean);

    Resource getById(long id);

    Resource getByCode(String code);

    List<ResourcePermission> queryMyResource(GetMyResourceBean bean);

    List<String> queryAllResourceByRoleCode(String roleCode);
}
