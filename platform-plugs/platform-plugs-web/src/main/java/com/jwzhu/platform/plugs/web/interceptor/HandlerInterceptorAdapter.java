package com.jwzhu.platform.plugs.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.common.SystemConfig;

@Component
public class HandlerInterceptorAdapter extends org.springframework.web.servlet.handler.HandlerInterceptorAdapter {

    @Autowired
    private SystemConfig systemConfig;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        if (modelAndView != null) {
            modelAndView.addObject("systemName", systemConfig.getName());
        }
    }
}
