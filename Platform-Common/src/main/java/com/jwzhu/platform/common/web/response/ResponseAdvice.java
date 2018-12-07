package com.jwzhu.platform.common.web.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.web.request.RequestBaseParam;

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

        if (body instanceof WebResult) {
            WebResult webResult = (WebResult) body;
            if (webResult.getCode() == null) {
                webResult.setCode(ResponseCode.SUCCESS.getCode());
            }
            if (webResult.getCostTime() == null) {
                webResult.setCostTime(RequestBaseParam.getCostTime());
            }
            return body;
        } else {
            WebResult<Object> webResult = new WebResult<>(body);
            webResult.setCode(ResponseCode.SUCCESS.getCode());
            webResult.setCostTime(RequestBaseParam.getCostTime());
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
}
