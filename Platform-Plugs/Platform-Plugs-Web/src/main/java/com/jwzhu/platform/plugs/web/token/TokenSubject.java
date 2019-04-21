package com.jwzhu.platform.plugs.web.token;

public class TokenSubject {

    private Long userId;
    private Short userType;
    private Long createMillis;

    public TokenSubject() {
    }

    public TokenSubject(Long userId) {
        this.userId = userId;
        this.createMillis = System.currentTimeMillis();
    }

    public TokenSubject(long userId, Short userType) {
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
