package com.jwzhu.platform.plugs.web.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.web.RequestInfo;
import com.jwzhu.platform.common.web.RequestUtil;
import com.jwzhu.platform.plugs.web.response.PageResult;
import com.jwzhu.platform.plugs.web.response.ResponseCode;
import com.jwzhu.platform.plugs.web.response.WebResult;
import com.jwzhu.platform.plugs.web.token.TokenService;

@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    private static Logger logger = LoggerFactory.getLogger(ResponseAdvice.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if ("/error".equals(request.getURI().getPath())) {
            return body;
        }

        response.getHeaders().setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        WebResult<?> webResult;
        if (body instanceof WebResult) {
            webResult = (WebResult<?>) body;
        } else {
            webResult = new WebResult<>(body);
        }

        if (webResult.getCode() == null) {
            webResult.setCode(ResponseCode.SUCCESS.getCode());
        }
        if (webResult.getCostTime() == null) {
            webResult.setCostTime(RequestInfo.getCostTime());
        }

        if (!StringUtil.isEmpty(RequestInfo.getRefreshToken())) {
            webResult.setToken(RequestInfo.getRefreshToken());
            logger.debug("存入Token：{}", RequestInfo.getRefreshToken());
            RequestUtil.setSession(tokenService.getTokenConfig().getParamName(), RequestInfo.getRefreshToken());
        }

        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(webResult);
            } catch (JsonProcessingException e) {
                throw new SystemException(e);
            }
        }else if(body instanceof PageBean){
            PageBean<Object> pageBean = (PageBean<Object>) body;
            PageResult<Object> pageResult = new PageResult<>();
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
