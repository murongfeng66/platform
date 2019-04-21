package com.jwzhu.platform.core.resource.db;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jwzhu.platform.core.resource.bean.QueryMenuBean;
import com.jwzhu.platform.core.resource.bean.ResourceBean;
import com.jwzhu.platform.core.resource.bean.ResourceListBean;
import com.jwzhu.platform.core.resource.model.Menu;
import com.jwzhu.platform.core.resource.model.Resource;
import com.jwzhu.platform.core.resource.model.ResourceList;

@Repository
public interface ResourceDao {

    void insert(ResourceBean bean);

    List<ResourceList> queryByParam(ResourceListBean bean);

    List<Menu> queryMenu(QueryMenuBean bean);

    int updateById(ResourceBean bean);

    Resource getById(long id);

    Resource getByCode(String code);

}
