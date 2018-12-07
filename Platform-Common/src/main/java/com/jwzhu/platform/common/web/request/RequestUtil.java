package com.jwzhu.platform.common.web.request;

import javax.servlet.http.HttpServletRequest;

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

    public static boolean isAjax(){
        HttpServletRequest request = getRequest();
        if(request != null){
            String requestedWith = request.getHeader("x-requested-with");
            return requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest");
        }
        return false;
    }

}
