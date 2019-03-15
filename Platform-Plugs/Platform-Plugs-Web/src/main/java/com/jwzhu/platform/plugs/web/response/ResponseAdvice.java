package com.jwzhu.platform.plugs.web.response;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;
import com.jwzhu.platform.plugs.web.request.RequestUtil;

@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if ("/error".equals(request.getURI().getPath())) {
            return body;
        }

        response.getHeaders().setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        WebResult webResult;
        if (body instanceof WebResult) {
            webResult = (WebResult) body;
        } else {
            webResult = new WebResult<>(body);
        }

        if (webResult.getCode() == null) {
            webResult.setCode(ResponseCode.SUCCESS.getCode());
        }
        if (webResult.getCostTime() == null) {
            webResult.setCostTime(RequestBaseParam.getCostTime());
        }

        if (!StringUtils.isEmpty(RequestBaseParam.getRefreshToken())) {
            webResult.setToken(RequestBaseParam.getRefreshToken());
            RequestUtil.setSession("Token", RequestBaseParam.getRefreshToken());
        }

        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(webResult);
            } catch (JsonProcessingException e) {
                throw new SystemException(e);
            }
        }else if(body instanceof PageBean){
            PageBean pageBean = (PageBean) body;
            PageResult<List> pageResult = new PageResult<>();
            pageResult.setTotalPage(pageBean.getTotalPage());
            pageResult.setPageSize(pageBean.getPageSize());
            pageResult.setTotalCount(pageBean.getTotalCount());
            pageResult.setCurrentPage(pageBean.getCurrentPage());
            pageResult.setData(pageBean.getList());
            return pageResult;
        }
        return webResult;
    }
}
