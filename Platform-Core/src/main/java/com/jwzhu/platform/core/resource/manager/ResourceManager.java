package com.jwzhu.platform.core.resource.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwzhu.platform.core.resource.bean.ResourceBean;
import com.jwzhu.platform.core.resource.bean.ResourceListBean;
import com.jwzhu.platform.core.resource.model.Menu;
import com.jwzhu.platform.core.resource.service.ResourceService;

@Service
public class ResourceManager {

    @Autowired
    private ResourceService resourceService;

    @Transactional
    public void insert(ResourceBean bean){
        resourceService.insert(bean);
    }

    public void queryByParam(ResourceListBean bean){
        resourceService.queryByParam(bean);
    }

    public List<Menu> queryMenu(long userId){
        return resourceService.queryMenu(userId);
    }

}
