package com.jwzhu.platform.core.resource.service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jwzhu.platform.core.resource.bean.ResourceBean;
import com.jwzhu.platform.core.resource.bean.ResourceListBean;
import com.jwzhu.platform.core.resource.db.ResourceDao;
import com.jwzhu.platform.core.resource.model.Menu;
import com.jwzhu.platform.core.resource.model.ResourceList;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;

@Service
public class ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    public void insert(ResourceBean bean) {
        bean.setCreateTime(RequestBaseParam.getRequestTime());
        resourceDao.insert(bean);
    }

    public List<ResourceList> queryByParam(ResourceListBean bean) {
        return resourceDao.queryByParam(bean);
    }

    public List<Menu> queryMenu(long userId){
        Map<String, Menu> map = resourceDao.queryMenu();
        List<Menu> menus = new LinkedList<>();
        for (Menu item : map.values()) {
            if(StringUtils.isEmpty(item.getParentCode())){
                menus.add(item);
            }else{
                Menu parent = map.get(item.getParentCode());
                if(parent != null){
                    if(parent.getChildren() == null){
                        parent.setChildren(new LinkedList<>());
                    }
                    parent.getChildren().add(item);
                }
            }
        }

        menus.sort(Comparator.comparingInt(Menu::getSort).reversed());
        return menus;
    }

}
