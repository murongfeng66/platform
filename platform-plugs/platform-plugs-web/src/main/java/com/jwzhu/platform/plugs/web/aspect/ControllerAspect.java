package com.jwzhu.platform.plugs.web.aspect;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwzhu.platform.common.web.RequestBaseParam;
import com.jwzhu.platform.common.web.RequestType;
import com.jwzhu.platform.common.web.TokenSubject;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.exception.JsonException;
import com.jwzhu.platform.plugs.web.exception.PageException;
import com.jwzhu.platform.plugs.web.exception.token.TokenEmptyException;
import com.jwzhu.platform.plugs.web.param.BaseParam;
import com.jwzhu.platform.plugs.web.permission.PermissionType;
import com.jwzhu.platform.plugs.web.request.RequestUtil;
import com.jwzhu.platform.plugs.web.token.TokenService;

@Aspect
@Component
public class ControllerAspect {

    private final static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenService tokenService;

    @Pointcut(value = "@annotation(controllerHandler)")
    public void pointCut(ControllerHandler controllerHandler) {
    }

    @Before(value = "pointCut(controllerHandler)", argNames = "joinPoint,controllerHandler")
    public void before(JoinPoint joinPoint, ControllerHandler controllerHandler) {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            logger.info("请求地址：{}", request.getRequestURI());
            initRequestType(request);
        }
        logger.info("请求接口：{}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

        String token = getToken(controllerHandler);

        if (controllerHandler.clearToken()) {
            clearToken();
        }

        if (StringUtils.isEmpty(token)) {
            if (PermissionType.No != controllerHandler.permissionType()) {
                throw new TokenEmptyException();
            }
            RequestBaseParam.setRequestToken(null);
            RequestBaseParam.setRequestUser(null);
        } else {
            logger.debug("取出Token：{}", token);
            try {
                analyzeToken(token);
            } catch (Exception e) {
                clearToken();
                throw e;
            }
            RequestBaseParam.setRequestToken(token);
        }

        controllerHandler.permissionType().check();

        RequestBaseParam.initRequestId();
        RequestBaseParam.initRequestTime();

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BaseParam) {
                BaseParam param = (BaseParam) arg;
                logger.info("请求参数：{}", JSON.toJSONString(param));
                if (controllerHandler.validParam()) {
                    param.valid(controllerHandler.validGroups());
                }
            }
        }
    }

    private void clearToken() {
        HttpServletResponse response = RequestUtil.getResponse();
        if(response != null){
            Cookie cookie = new Cookie(tokenService.getTokenConfig().getParamName(), null);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        HttpServletRequest request = RequestUtil.getRequest();
        if(request != null){
            request.getSession().removeAttribute(tokenService.getTokenConfig().getParamName());
        }
    }

    private void initRequestType(HttpServletRequest request) {
        String requestTypeString = request.getParameter("requestType");
        if (StringUtils.isEmpty(requestTypeString)) {
            RequestBaseParam.initRequestType(RequestUtil.isAjax() ? RequestType.Ajax.getCode() : RequestType.Page.getCode());
        } else {
            RequestBaseParam.initRequestType(Short.valueOf(requestTypeString));
        }
    }

    private String getToken(ControllerHandler controllerHandler) {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request == null) {
            return null;
        }

        String token = request.getParameter(tokenService.getTokenConfig().getParamName());
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader(tokenService.getTokenConfig().getParamName());

            if (!StringUtils.isEmpty(token)) {
                logger.info("Token来源：请求头");
            }
        } else {
            logger.info("Token来源：请求参数");
        }

        if (StringUtils.isEmpty(token)) {
            Object temp = request.getSession().getAttribute(tokenService.getTokenConfig().getParamName());
            if (!StringUtils.isEmpty(temp)) {
                token = temp.toString();
            }

            if (!StringUtils.isEmpty(token)) {
                logger.info("Token来源：session");
            }
        }

        if (StringUtils.isEmpty(token)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(tokenService.getTokenConfig().getParamName()) && !StringUtils.isEmpty(cookie.getValue())) {
                        token = cookie.getValue();
                    }
                }
            }

            if (!StringUtils.isEmpty(token)) {
                logger.info("Token来源：cookie");
            }
        }

        return token;
    }

    private void analyzeToken(String token) {
        TokenSubject subject = tokenService.checkToken(token);
        RequestBaseParam.setRefreshToken(tokenService.updateToken(subject));
        RequestBaseParam.setRequestUser(subject);
    }

    @AfterThrowing(value = "pointCut(controllerHandler)", argNames = "e,controllerHandler", throwing = "e")
    public void afterThrowing(Throwable e, ControllerHandler controllerHandler) {
        logger.error("接口报错\n");
        wrapException(e);
    }

    private void wrapException(Throwable e) {
        if (RequestBaseParam.getRequestType() == RequestType.Ajax) {
            throw new JsonException(e);
        } else {
            throw new PageException(e);
        }
    }

    @After(value = "pointCut(controllerHandler)", argNames = "controllerHandler")
    public void after(ControllerHandler controllerHandler) {
        RequestBaseParam.initResponseTime();
        logger.info("接口耗时：{}\n", RequestBaseParam.getCostTime());
    }

    @AfterReturning(value = "pointCut(controllerHandler)", argNames = "controllerHandler,returnValue", returning = "returnValue")
    public void afterReturning(ControllerHandler controllerHandler, Object returnValue) throws JsonProcessingException {
        if (controllerHandler.printResponse()) {
            logger.info("接口响应：{}\n", objectMapper.writeValueAsString(returnValue));
        }
    }

}
