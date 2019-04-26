package com.jwzhu.platform.core.service.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.common.bean.UpdateStatusBean;
import com.jwzhu.platform.core.service.bean.ServiceBean;
import com.jwzhu.platform.core.service.bean.ServiceListBean;
import com.jwzhu.platform.core.service.bean.ServiceMemberBean;
import com.jwzhu.platform.core.service.bean.ServiceMemberListBean;
import com.jwzhu.platform.core.service.model.Service;
import com.jwzhu.platform.core.service.model.ServiceMember;

@Repository
public interface ServiceDao {

    void insert(ServiceBean bean);

    int updateById(ServiceBean bean);

    List<Service> queryByParam(ServiceListBean bean);

    Service getById(long id);

    Service getByServiceCode(String serviceCode);

    void addMember(ServiceMemberBean bean);

    int removeMember(ServiceMemberBean bean);

    boolean existsMember(ServiceMemberBean bean);

    List<ServiceMember> queryMember(ServiceMemberListBean bean);

    int updateStatus(UpdateStatusBean bean);

}
