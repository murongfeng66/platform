package com.jwzhu.platform.plugs.web.token;

public class TokenSubject {

    private Long id;
    private Short type;
    private Long time;
    private Long sId;

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

    public Long getsId() {
        return sId;
    }

    public void setsId(Long serviceId) {
        this.sId = serviceId;
    }
}
