package com.jwzhu.platform.core.permission.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.bean.StringBean;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.permission.bean.GetRoleResourceBean;
import com.jwzhu.platform.core.permission.bean.PermissionSaveBean;
import com.jwzhu.platform.core.permission.bean.ResourceBean;
import com.jwzhu.platform.core.permission.bean.ResourceListBean;
import com.jwzhu.platform.core.permission.model.Menu;
import com.jwzhu.platform.core.permission.model.Resource;
import com.jwzhu.platform.core.permission.model.ResourcePermission;
import com.jwzhu.platform.core.permission.param.GetRoleResourceParam;
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

    public List<ResourcePermission> queryMyResource(GetRoleResourceBean bean) {
        return resourceService.queryMyResource(bean);
    }

    @Transactional
    public void savePermission(PermissionSaveBean bean){
        resourceService.savePermission(bean);
    }
}
