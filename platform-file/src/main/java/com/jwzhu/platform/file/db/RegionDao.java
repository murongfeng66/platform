package com.jwzhu.platform.file.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.file.bean.RegionBean;
import com.jwzhu.platform.file.bean.RegionListBean;
import com.jwzhu.platform.file.model.Region;

@Repository
public interface RegionDao {

    void insert(RegionBean bean);

    int updateById(RegionBean bean);

    List<Region> queryByParam(RegionListBean bean);

}
