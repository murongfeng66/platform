package com.jwzhu.platform.file.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.util.StringUtil;
import com.jwzhu.platform.common.web.RequestBaseParam;
import com.jwzhu.platform.file.bean.RegionBean;
import com.jwzhu.platform.file.bean.RegionListBean;
import com.jwzhu.platform.file.db.RegionDao;
import com.jwzhu.platform.file.model.Region;

@Service
public class RegionService {

    @Autowired
    private RegionDao regionDao;

    public void insert(RegionBean bean) {
        bean.setCreateTime(bean.getCreateTime() == null ? RequestBaseParam.getRequestTime() : bean.getCreateTime());
        bean.setRegionStatus(bean.getRegionStatus() == null ? AvailableStatus.Enable.getCode() : bean.getRegionStatus());
        bean.setRegionCode(bean.getRegionCode() == null ? StringUtil.createCode("FR") : bean.getRegionCode());
        regionDao.insert(bean);
    }

    public List<Region> queryByParam(RegionListBean bean) {
        return regionDao.queryByParam(bean);
    }

}
