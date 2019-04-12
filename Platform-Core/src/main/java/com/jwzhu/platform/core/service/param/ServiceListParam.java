package com.jwzhu.platform.core.service.param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.jwzhu.platform.core.service.bean.ServiceListBean;
import com.jwzhu.platform.plugs.web.param.PageParam;

public class ServiceListParam extends PageParam<ServiceListBean> {

    private String key;
    private Long ownerId;
    private Short availableStatus;
    private LocalDateTime datetime;
    private LocalDate date;
    private LocalTime time;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Short getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(Short availableStatus) {
        this.availableStatus = availableStatus;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    protected ServiceListBean getBean() {
        return new ServiceListBean();
    }
}
