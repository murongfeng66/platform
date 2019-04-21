package com.jwzhu.platform.core.resource.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.enums.YesOrNo;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.resource.bean.QueryMenuBean;
import com.jwzhu.platform.core.resource.bean.ResourceBean;
import com.jwzhu.platform.core.resource.bean.ResourceListBean;
import com.jwzhu.platform.core.resource.db.ResourceDao;
import com.jwzhu.platform.core.resource.model.Menu;
import com.jwzhu.platform.core.resource.model.Resource;
import com.jwzhu.platform.core.resource.model.ResourceList;
import com.jwzhu.platform.core.resource.model.ResourceType;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;

@Service
public class ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    public void insert(ResourceBean bean) {
        Resource resource = resourceDao.getByCode(bean.getCode());
        if(resource != null){
            throw new BusinessException("存在相同的资源编号");
        }
        bean.setCreateTime(bean.getCreateTime() == null ? RequestBaseParam.getRequestTime() : bean.getCreateTime());
        bean.setAvailableStatus(bean.getAvailableStatus() == null ? AvailableStatus.Enable.getCode() : bean.getAvailableStatus());
        bean.setMenuShow(bean.getMenuShow() == null ? YesOrNo.No.getCode() : bean.getMenuShow());
        bean.setSort(bean.getSort() == null ? 10 : bean.getSort());
        resourceDao.insert(bean);
    }

    public List<ResourceList> queryByParam(ResourceListBean bean) {
        return resourceDao.queryByParam(bean);
    }

    public List<Menu> queryMenu() {
        QueryMenuBean bean = new QueryMenuBean();
        bean.setUserId(RequestBaseParam.getRequestUser().getUserId());
        bean.setMenuShow(YesOrNo.Yes.getCode());
        bean.setAvailableStatus(AvailableStatus.Enable.getCode());
        bean.setTypes(ResourceType.Menu.getCode(), ResourceType.Page.getCode());
        List<Menu> resources = resourceDao.queryMenu(bean);
        Map<String, Menu> resourceMap = new LinkedHashMap<>();
        for (Menu resource : resources) {
            resourceMap.put(resource.getCode(), resource);
        }

        List<Menu> menus = new LinkedList<>();
        for (Menu item : resourceMap.values()) {
            if (StringUtils.isEmpty(item.getParentCode())) {
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

//        menus.sort(Comparator.comparingInt(Menu::getSort).reversed());
        return menus;
    }

    public void updateById(ResourceBean bean) {
        bean.setUpdateTime(bean.getUpdateTime() == null ? RequestBaseParam.getRequestTime() : bean.getUpdateTime());
        if (resourceDao.updateById(bean) == 0) {
            throw new BusinessException("更新失败");
        }
    }

    public Resource getById(long id){
        return resourceDao.getById(id);
    }

}
