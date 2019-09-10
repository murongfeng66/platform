package com.jwzhu.platform.plugs.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.common.SystemConfig;
import com.jwzhu.platform.common.util.StringUtil;

@Component
public class HandlerInterceptorAdapter extends org.springframework.web.servlet.handler.HandlerInterceptorAdapter {

    @Autowired
    private SystemConfig systemConfig;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view) throws Exception {
        super.postHandle(request, response, handler, view);
        if (view == null) {
            return;
        }
        String viewName = view.getViewName();
        if (StringUtil.isEmpty(viewName)) {
            return;
        }
        if (viewName.startsWith("redirect")) {
            return;
        }
        view.addObject("systemName", systemConfig.getName());
    }
}
