package com.jwzhu.platform.file.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.file.bean.RegionBean;
import com.jwzhu.platform.file.bean.RegionListBean;
import com.jwzhu.platform.file.model.Region;
import com.jwzhu.platform.file.service.RegionService;

@Service
public class RegionManager {

    @Autowired
    private RegionService regionService;

    public void insert(RegionBean bean) {
        regionService.insert(bean);
    }

    public List<Region> queryByParam(RegionListBean bean){
        return regionService.queryByParam(bean);
    }

}
