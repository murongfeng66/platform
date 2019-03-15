package com.jwzhu.platform.plugs.web.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtil {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return requestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return requestAttributes.getResponse();
    }

    public static boolean isAjax(){
        HttpServletRequest request = getRequest();
        if(request != null){
            String requestedWith = request.getHeader("x-requested-with");
            return requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest");
        }
        return false;
    }

    public static void setSession(String name, String value){
        HttpServletRequest request = getRequest();
        if(request != null){
            request.getSession().setAttribute(name, value);
        }
    }

    public static String getSession(String name){
        HttpServletRequest request = getRequest();
        if(request != null){
            return request.getSession().getAttribute(name).toString();
        }
        return null;
    }

}
