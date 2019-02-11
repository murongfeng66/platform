package com.jwzhu.platform.web.base.token;

public class TokenSubject {

    private Long userId;
    private Short userType;
    private Long createMillis;

    public TokenSubject() {
    }

    public TokenSubject(long userId, short userType) {
        this.userId = userId;
        this.userType = userType;
        this.createMillis = System.currentTimeMillis();
    }

    public Long getCreateMillis() {
        return createMillis;
    }

    public void setCreateMillis(Long createMillis) {
        this.createMillis = createMillis;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getUserType() {
        return userType;
    }

    public void setUserType(Short userType) {
        this.userType = userType;
    }
}
