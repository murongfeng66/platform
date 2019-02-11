package com.jwzhu.platform.web.base.request;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.jwzhu.platform.common.date.DateUtil;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.web.base.token.TokenSubject;

public class RequestBaseParam {
    /**
     * 请求ID
     */
    private static ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();
    /**
     * 请求时间
     */
    private static ThreadLocal<Timestamp> REQUEST_TIME = new ThreadLocal<>();
    /**
     * 响应时间
     */
    private static ThreadLocal<Timestamp> RESPONSE_TIME = new ThreadLocal<>();
    /**
     * 处理时长
     */
    private static ThreadLocal<Long> COST_TIME = new ThreadLocal<>();
    /**
     * 请求类型
     */
    private static ThreadLocal<RequestType> REQUEST_TYPE = new ThreadLocal<>();
    /**
     * 处理时长
     */
    private static ThreadLocal<TokenSubject> REQUEST_USER = new ThreadLocal<>();
    /**
     * 当设置了Token刷新机制时，生成的新Token
     */
    private static ThreadLocal<String> NEW_TOKEN = new ThreadLocal<>();

    /**
     * 初始化请求ID
     */
    public static void initRequestId() {
        REQUEST_ID.set(UUID.randomUUID().toString());
    }

    /**
     * 初始化请求时间
     */
    public static void initRequestTime() {
        REQUEST_TIME.set(DateUtil.now());
    }

    /**
     * 初始化响应时间
     */
    public static void initResponseTime() {
        RESPONSE_TIME.set(DateUtil.now());
        if (REQUEST_TIME.get() == null) {
            initRequestTime();
        }
        COST_TIME.set(RESPONSE_TIME.get().getTime() - REQUEST_TIME.get().getTime());
    }

    /**
     * 初始化请求类型
     */
    public static void initRequestType(Short requestType) {
        if (requestType == null) {
            REQUEST_TYPE.set(RequestType.Page);
        } else {
            try {
                REQUEST_TYPE.set(RequestType.get(requestType));
            } catch (SystemException e) {
                REQUEST_TYPE.set(RequestType.Page);
            }
        }
    }

    /**
     * 获取请求ID
     */
    public static String getRequestId() {
        String requestId = REQUEST_ID.get();
        if (StringUtils.isEmpty(requestId)) {
            throw new SystemException("请求ID为空");
        }
        return requestId;
    }

    /**
     * 获取请求时间
     */
    public static Timestamp getRequestTime() {
        Timestamp requestTime = REQUEST_TIME.get();
        if (requestTime == null) {
            throw new SystemException("请求时间为空");
        }
        return requestTime;
    }

    /**
     * 获取请求时间
     */
    public static Timestamp getResponseTime() {
        Timestamp responseTime = RESPONSE_TIME.get();
        if (responseTime == null) {
            throw new SystemException("响应时间为空");
        }
        return responseTime;
    }

    /**
     * 获取请求类型
     */
    public static RequestType getRequestType() {
        RequestType requestType = REQUEST_TYPE.get();
        if (requestType == null) {
            throw new SystemException("请求类型为空");
        }
        return requestType;
    }

    /**
     * 获取响应时长
     */
    public static long getCostTime() {
        Long costTime = COST_TIME.get();
        if (costTime == null) {
            initResponseTime();
        }
        return COST_TIME.get();
    }

    public static void setRequestUser(TokenSubject subject){
        REQUEST_USER.set(subject);
    }

    public static TokenSubject getRequestUser() {
        TokenSubject subject = REQUEST_USER.get();
        if(subject == null){
            throw new BusinessException("无权限");
        }
        return subject;
    }

    public static void setNewToken(String token){
        NEW_TOKEN.set(token);
    }

    public static String getNewToken(){
        return NEW_TOKEN.get();
    }
}
