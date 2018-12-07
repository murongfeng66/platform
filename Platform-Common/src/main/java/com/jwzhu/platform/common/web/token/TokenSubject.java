package com.jwzhu.platform.common.web.token;

public class TokenSubject {

    private Long userId;
    private Short userType;
    private Long currentMilli;

    public TokenSubject() {
        this.currentMilli = System.currentTimeMillis();
    }

    public TokenSubject(long userId, short userType) {
        this.userId = userId;
        this.userType = userType;
        this.currentMilli = System.currentTimeMillis();
    }

    public Long getCurrentMilli() {
        return currentMilli;
    }

    public void setCurrentMilli(Long currentMilli) {
        this.currentMilli = currentMilli;
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
