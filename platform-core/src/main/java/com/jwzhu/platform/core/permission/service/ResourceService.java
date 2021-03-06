package com.jwzhu.platform.core.permission.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.bean.LongBean;
import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.common.enums.AdminType;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.enums.YesOrNo;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.util.StringUtil;
import com.jwzhu.platform.common.web.RequestInfo;
import com.jwzhu.platform.core.permission.bean.GetRoleResourceBean;
import com.jwzhu.platform.core.permission.bean.QueryMenuBean;
import com.jwzhu.platform.core.permission.bean.ResourceBean;
import com.jwzhu.platform.core.permission.bean.ResourceListBean;
import com.jwzhu.platform.core.permission.db.ResourceDao;
import com.jwzhu.platform.core.permission.model.*;

@Service
public class ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    public void insert(ResourceBean bean) {
        if (resourceDao.getByCode(bean.getCode()) != null) {
            throw new BusinessException("存在相同的资源编号");
        }
        if (!StringUtil.isEmpty(bean.getUrl()) && resourceDao.getByUrl(bean.getUrl()) != null) {
            throw new BusinessException("存在相同的资源URL");
        }
        bean.setCreateTime(bean.getCreateTime() == null ? RequestInfo.getRequestTime() : bean.getCreateTime());
        bean.setResourceStatus(bean.getResourceStatus() == null ? AvailableStatus.Enable.getCode() : bean.getResourceStatus());
        bean.setMenuShow(bean.getMenuShow() == null ? YesOrNo.No.getCode() : bean.getMenuShow());
        bean.setSort(bean.getSort() == null ? 10 : bean.getSort());
        resourceDao.insert(bean);
    }

    public List<ResourceList> queryByParam(ResourceListBean bean) {
        return resourceDao.queryByParam(bean);
    }

    public List<Menu> queryMenu() {
        QueryMenuBean bean = new QueryMenuBean();
        if (RequestInfo.getRequestUser().getType() != AdminType.Super.getCode()) {
            bean.setSelfId(RequestInfo.getRequestUser().getId());
        }
        bean.setMenuShow(YesOrNo.Yes.getCode());
        bean.setEnableStatusCode(AvailableStatus.Enable.getCode());
        bean.setTypes(ResourceType.Menu.getCode(), ResourceType.Page.getCode());
        List<Menu> resources = resourceDao.queryMenu(bean);
        Map<String, Menu> resourceMap = new LinkedHashMap<>();
        for (Menu resource : resources) {
            resourceMap.put(resource.getCode(), resource);
        }

        List<Menu> menus = new LinkedList<>();
        for (Menu item : resourceMap.values()) {
            if (StringUtil.isEmpty(item.getParentCode())) {
                menus.add(item);
            } else {
                Menu parent = resourceMap.get(item.getParentCode());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new LinkedList<>());
                    }
                    parent.getChildren().add(item);
                }
            }
        }

        return menus;
    }

    public void updateById(ResourceBean bean) {
        bean.setUpdateTime(bean.getUpdateTime() == null ? RequestInfo.getRequestTime() : bean.getUpdateTime());
        if (resourceDao.updateById(bean) == 0) {
            throw new BusinessException("更新失败");
        }
    }

    public Resource getById(long id) {
        return resourceDao.getById(id);
    }

    public List<ResourcePermission> queryRoleResource(GetRoleResourceBean bean) {
        if (RequestInfo.getRequestUser().getType() != AdminType.Super.getCode()) {
            bean.setSelfId(RequestInfo.getRequestUser().getId());
        }
        bean.setEnableStatusCode(AvailableStatus.Enable.getCode());

        List<ResourcePermission> list = resourceDao.queryRoleResource(bean);
        Map<String, ResourcePermission> map = list.stream().collect(Collectors.toMap(ResourcePermission::getCode, a -> a, (k1, k2) -> k2));

        List<String> roleResource = resourceDao.queryAllResourceByRoleCode(bean.getRoleCode());

        List<ResourcePermission> result = new LinkedList<>();
        for (ResourcePermission permission : list) {
            permission.setHave(roleResource.contains(permission.getCode()));

            if (StringUtil.isEmpty(permission.getParentCode())) {
                result.add(permission);
            } else if (map.containsKey(permission.getParentCode())) {
                ResourcePermission parent = map.get(permission.getParentCode());
                if (parent.getChildren() == null) {
                    parent.setChildren(new LinkedList<>());
                }
                map.get(permission.getParentCode()).getChildren().add(permission);
            }
        }
        return result;
    }

    public List<String> queryAdminResourceUrl(long adminId, short userType) {
        GetRoleResourceBean bean = new GetRoleResourceBean();
        if (userType != AdminType.Super.getCode()) {
            bean.setSelfId(adminId);
        }
        bean.setEnableStatusCode(AvailableStatus.Enable.getCode());
        return resourceDao.queryMyResourceUrl(bean);
    }

    public List<String> queryAdminResourceCode(long adminId, short userType) {
        GetRoleResourceBean bean = new GetRoleResourceBean();
        if (userType != AdminType.Super.getCode()) {
            bean.setSelfId(adminId);
        }
        bean.setEnableStatusCode(AvailableStatus.Enable.getCode());
        return resourceDao.queryRoleResource(bean).stream().map(ResourcePermission::getCode).collect(Collectors.toList());
    }

    private void updateStatus(UpdateStatusBean bean, String errorMessage) {
        bean.setUpdateTime(bean.getUpdateTime() == null ? RequestInfo.getRequestTime() : bean.getUpdateTime());
        if (resourceDao.updateStatus(bean) == 0) {
            throw new BusinessException(errorMessage);
        }
    }

    public void disable(LongBean bean) {
        UpdateStatusBean statusBean = new UpdateStatusBean();
        statusBean.setId(bean.getId());
        statusBean.setOldStatus(AvailableStatus.Enable.getCode());
        statusBean.setNewStatus(AvailableStatus.Disable.getCode());
        updateStatus(statusBean, "禁用资源失败");
    }

    public void enable(LongBean bean) {
        UpdateStatusBean statusBean = new UpdateStatusBean();
        statusBean.setId(bean.getId());
        statusBean.setOldStatus(AvailableStatus.Disable.getCode());
        statusBean.setNewStatus(AvailableStatus.Enable.getCode());
        updateStatus(statusBean, "启用资源失败");
    }

    public void delete(LongBean bean) {
        UpdateStatusBean statusBean = new UpdateStatusBean();
        statusBean.setId(bean.getId());
        statusBean.setOldStatus(AvailableStatus.Disable.getCode());
        statusBean.setNewStatus(AvailableStatus.Delete.getCode());
        updateStatus(statusBean, "删除资源失败");
    }
}
