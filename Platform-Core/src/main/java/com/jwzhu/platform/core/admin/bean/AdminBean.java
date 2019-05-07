package com.jwzhu.platform.core.admin.bean;

import java.time.LocalDateTime;

import com.jwzhu.platform.common.bean.BaseBean;

public class AdminBean extends BaseBean {

    private Long id;
    private String nickname;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Short adminStatus;
    private Short adminType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Short getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Short adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Short getAdminType() {
        return adminType;
    }

    public void setAdminType(Short adminType) {
        this.adminType = adminType;
    }
}
