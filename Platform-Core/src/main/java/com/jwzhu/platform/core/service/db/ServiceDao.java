package com.jwzhu.platform.core.service.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.core.service.bean.ServiceBean;
import com.jwzhu.platform.core.service.bean.ServiceListBean;
import com.jwzhu.platform.core.service.model.Service;

@Repository
public interface ServiceDao {

    void insert(ServiceBean bean);

    int updateById(ServiceBean bean);

    List<Service> queryByParam(ServiceListBean bean);

    Service getById(long id);

}
