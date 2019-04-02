package com.jwzhu.platform.plugs.web.request;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.exception.NoPermissionException;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.plugs.web.token.TokenSubject;

public class RequestBaseParam {
    /**
     * 请求ID
     */
    private static ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();
    /**
     * 请求时间
     */
    private static ThreadLocal<LocalDateTime> REQUEST_TIME = new ThreadLocal<>();
    /**
     * 响应时间
     */
    private static ThreadLocal<LocalDateTime> RESPONSE_TIME = new ThreadLocal<>();
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
     * 刷新Token
     */
    private static ThreadLocal<String> REFRESH_TOKEN = new ThreadLocal<>();

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
        REQUEST_TIME.set(LocalDateTime.now());
    }

    /**
     * 初始化响应时间
     */
    public static void initResponseTime() {
        if (REQUEST_TIME.get() == null) {
            initRequestTime();
        }
        RESPONSE_TIME.set(LocalDateTime.now());
        COST_TIME.set(Duration.between(REQUEST_TIME.get(),  RESPONSE_TIME.get()).toMillis());
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
    public static LocalDateTime getRequestTime() {
        LocalDateTime requestTime = REQUEST_TIME.get();
        if (requestTime == null) {
            throw new SystemException("请求时间为空");
        }
        return requestTime;
    }

    /**
     * 获取请求时间
     */
    public static LocalDateTime getResponseTime() {
        LocalDateTime responseTime = RESPONSE_TIME.get();
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
            throw new NoPermissionException();
        }
        return subject;
    }

    public static void setRefreshToken(String token){
        REFRESH_TOKEN.set(token);
    }

    public static String getRefreshToken(){
        return REFRESH_TOKEN.get();
    }
}
