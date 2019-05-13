package com.jwzhu.platform.common.bean;

import java.time.LocalDateTime;

public class UpdateStatusBean extends BaseBean {

    private Object id;
    private Short oldStatus;
    private Short newStatus;
    private LocalDateTime updateTime;

    public UpdateStatusBean() {
        this.updateTime = LocalDateTime.now();
    }

    public UpdateStatusBean(Object id, Short oldStatus, Short newStatus) {
        this.id = id;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Short getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Short oldStatus) {
        this.oldStatus = oldStatus;
    }

    public Short getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Short newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
