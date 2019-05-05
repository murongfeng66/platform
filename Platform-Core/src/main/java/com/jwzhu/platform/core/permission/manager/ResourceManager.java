package com.jwzhu.platform.core.permission.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.permission.bean.ResourceBean;
import com.jwzhu.platform.core.permission.bean.ResourceListBean;
import com.jwzhu.platform.core.permission.model.Menu;
import com.jwzhu.platform.core.permission.model.Resource;
import com.jwzhu.platform.core.permission.service.ResourceService;

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

    public List<Menu> queryMenu(){
        return resourceService.queryMenu();
    }

    public void updateById(ResourceBean bean){
        resourceService.updateById(bean);
    }

    public Resource getById(LongBean bean){
        Resource resource = resourceService.getById(bean.getId());
        if(resource == null){
            throw new BusinessException("无此资源");
        }
        return resource;
    }

}
