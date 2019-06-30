package com.jwzhu.platform.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jwzhu.platform.common.exception.SystemException;

public class RequestUtil {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new SystemException("ServletRequestAttributes为空");
        }
        return requestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new SystemException("ServletRequestAttributes为空");
        }
        return requestAttributes.getResponse();
    }

    public static boolean isAjax() {
        String requestedWith = getRequest().getHeader("x-requested-with");
        return requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest");
    }

    public static void setSession(String name, String value) {
        getRequest().getSession().setAttribute(name, value);
    }

    public static String getSession(String name) {
        return getRequest().getSession().getAttribute(name).toString();
    }

}
