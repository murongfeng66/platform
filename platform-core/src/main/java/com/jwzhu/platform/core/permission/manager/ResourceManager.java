package com.jwzhu.platform.core.permission.manager;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwzhu.platform.common.SystemConfig;
import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.web.RequestInfo;
import com.jwzhu.platform.common.web.TokenSubject;
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
public class ResourceManager implements PermissionService {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private SystemConfig systemConfig;

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

    @Override
    public boolean checkNoPermission(String url) {
        String cacheKey = getCacheKey(RequestInfo.getRequestUser().getId(), PermissionCacheType.Url);
        if (cacheUtil.exist(cacheKey)) {
            return !cacheUtil.sExists(cacheKey, url);
        }

        List<String> urls = resourceService.queryAdminResourceUrl(RequestInfo.getRequestUser().getId(), RequestInfo.getRequestUser().getType());
        cacheUtil.sAdd(cacheKey, urls.toArray(new String[]{}));
        cacheUtil.expired(cacheKey, systemConfig.getResourceTimeout().toMillis());
        return !urls.contains(url);
    }

    @Override
    public Collection<String> getPermissions(PermissionCacheType type) {
        type = type == null ? PermissionCacheType.Url : type;
        TokenSubject subject = RequestInfo.getRequestUser();
        String cacheKey = getCacheKey(subject.getId(), type);
        if (cacheUtil.exist(cacheKey)) {
            return cacheUtil.sMembers(cacheKey);
        }
        Collection<String> permissions = type == PermissionCacheType.Code ? resourceService.queryAdminResourceCode(subject.getId(), subject.getType()) : resourceService.queryAdminResourceUrl(subject.getId(), subject.getType());
        cacheUtil.sAdd(cacheKey, permissions.toArray(new String[]{}));
        cacheUtil.expired(cacheKey, systemConfig.getResourceTimeout().toMillis());
        return permissions;
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
