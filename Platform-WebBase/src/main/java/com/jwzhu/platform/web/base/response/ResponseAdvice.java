package com.jwzhu.platform.web.base.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwzhu.platform.common.bean.ResponseCode;
import com.jwzhu.platform.common.bean.WebResult;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.web.base.request.RequestBaseParam;

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
        if("/error".equals(request.getURI().getPath())){
            return body;
        }

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
        webResult.setToken(RequestBaseParam.getNewToken());

        if(body instanceof String){
            try {
                return objectMapper.writeValueAsString(webResult);
            } catch (JsonProcessingException e) {
                throw new SystemException(e);
            }
        }
        return webResult;
    }
}
