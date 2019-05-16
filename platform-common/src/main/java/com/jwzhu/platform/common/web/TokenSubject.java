package com.jwzhu.platform.common.web;

public class TokenSubject {

    private Long id;
    private Short type;
    private Long time;

    public TokenSubject() {
        this.time = System.currentTimeMillis();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long createMillis) {
        this.time = createMillis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short userType) {
        this.type = userType;
    }
}
