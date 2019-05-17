package com.jwzhu.platform.core.permission.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwzhu.platform.common.PlatformConfig;
import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.web.RequestBaseParam;
import com.jwzhu.platform.core.admin.model.Admin;
import com.jwzhu.platform.core.admin.service.AdminService;
import com.jwzhu.platform.core.permission.bean.GetRoleResourceBean;
import com.jwzhu.platform.core.permission.bean.ResourceBean;
import com.jwzhu.platform.core.permission.bean.ResourceListBean;
import com.jwzhu.platform.core.permission.model.Menu;
import com.jwzhu.platform.core.permission.model.Resource;
import com.jwzhu.platform.core.permission.model.ResourcePermission;
import com.jwzhu.platform.core.permission.service.ResourceService;
import com.jwzhu.platform.permission.PermissionService;
import com.jwzhu.platform.plugs.cache.base.CacheUtil;

@Service
public class ResourceManager implements PermissionService{

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private PlatformConfig platformConfig;
    @Autowired
    private AdminService adminService;

    @Transactional
    public void insert(ResourceBean bean) {
        resourceService.insert(bean);
    }

    public void queryByParam(ResourceListBean bean) {
        resourceService.queryByParam(bean);
    }

    public List<Menu> queryMenu() {
        return resourceService.queryMenu();
    }

    public void updateById(ResourceBean bean) {
        resourceService.updateById(bean);
    }

    public Resource getById(LongBean bean) {
        Resource resource = resourceService.getById(bean.getId());
        if (resource == null) {
            throw new BusinessException("无此资源");
        }
        return resource;
    }

    public List<ResourcePermission> queryRoleResource(GetRoleResourceBean bean) {
        return resourceService.queryRoleResource(bean);
    }

    public List<String> queryAdminResourceUrl(long adminId) {
        Admin admin = adminService.getById(adminId);
        return resourceService.queryAdminResourceUrl(adminId, admin.getAdminType());
    }

    @Override
    public boolean checkPermission(String url) {
        String cacheKey = getCacheKey(RequestBaseParam.getRequestUser().getId());
        if (cacheUtil.exist(cacheKey)) {
            return cacheUtil.sExists(cacheKey, url);
        } else {
            List<String> urls = resourceService.queryAdminResourceUrl(RequestBaseParam.getRequestUser().getId(), RequestBaseParam.getRequestUser().getType());
            cacheUtil.sAdd(cacheKey, urls.toArray(new String[]{}));
            cacheUtil.expired(cacheKey, platformConfig.getResourceTimeout().toMillis());
            return urls.contains(url);
        }
    }

    public void disable(LongBean bean) {
        resourceService.disable(bean);
    }

    public void enable(LongBean bean) {
        resourceService.enable(bean);
    }

    public void delete(LongBean bean) {
        resourceService.delete(bean);
    }
}
