package com.jwzhu.platform.core.admin.model;

import java.time.LocalDateTime;

import com.jwzhu.platform.common.enums.AdminType;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.plugs.jsonescape.bind.EnumEscape;

public class Admin {

    private Long id;
    private String nickname;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @EnumEscape(AvailableStatus.class)
    private Short adminStatus;
    @EnumEscape(AdminType.class)
    private Short adminType;

    public Short getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Short adminStatus) {
        this.adminStatus = adminStatus;
    }

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

    public Short getAdminType() {
        return adminType;
    }

    public void setAdminType(Short adminType) {
        this.adminType = adminType;
    }
}
