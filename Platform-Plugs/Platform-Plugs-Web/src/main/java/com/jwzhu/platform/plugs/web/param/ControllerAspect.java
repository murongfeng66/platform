package com.jwzhu.platform.plugs.web.param;

import java.io.IOException;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.plugs.web.exception.JsonException;
import com.jwzhu.platform.plugs.web.exception.PageException;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;
import com.jwzhu.platform.plugs.web.request.RequestType;
import com.jwzhu.platform.plugs.web.request.RequestUtil;
import com.jwzhu.platform.plugs.web.token.TokenService;
import com.jwzhu.platform.plugs.web.token.TokenSubject;

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
        }
        logger.info("请求接口：{}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

        RequestBaseParam.initRequestId();
        RequestBaseParam.initRequestTime();
        RequestBaseParam.initRequestType(RequestUtil.isAjax() ? RequestType.Ajax.getCode() : null);

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BaseParam) {
                BaseParam param = (BaseParam) arg;

                if (param.getRequestType() != null) {
                    RequestBaseParam.initRequestType(param.getRequestType());
                }

                if (controllerHandler.validParam()) {
                    param.valid(controllerHandler.validGroups());
                }
            }
        }

        String token = analyzeToken(controllerHandler);

        if(controllerHandler.needToken() && StringUtils.isEmpty(token)){
            HttpServletResponse response = RequestUtil.getResponse();
            if(response != null){
                try {
                    response.sendRedirect("/login");
                } catch (IOException e) {
                    throw new SystemException(e);
                }
            }
        }
    }

    private String analyzeToken(ControllerHandler controllerHandler) {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request == null) {
            return null;
        }

        String token = request.getParameter(tokenService.getTokenConfig().getParamName());
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader(tokenService.getTokenConfig().getParamName());
        } else {
            logger.info("Token来源：请求参数");
        }
        if (StringUtils.isEmpty(token)) {
            Object temp = request.getSession().getAttribute(tokenService.getTokenConfig().getParamName());
            if(!StringUtils.isEmpty(temp)){
                token = temp.toString();
                if(controllerHandler.clearToken()){
                    request.getSession().removeAttribute(tokenService.getTokenConfig().getParamName());
                }
            }
        } else {
            logger.info("Token来源：请求头");
        }

        if (!StringUtils.isEmpty(token)) {
            logger.info("Token来源：session");
            TokenSubject subject = tokenService.checkToken(token);
            RequestBaseParam.setRefreshToken(tokenService.updateToken(subject));
            RequestBaseParam.setRequestUser(subject);
        }
        return token;
    }

    @AfterThrowing(value = "pointCut(controllerHandler)", argNames = "e,controllerHandler", throwing = "e")
    public void afterThrowing(Throwable e, ControllerHandler controllerHandler) {
        logger.error("controller end with a exception\n");
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
        logger.info("controller end and costTime is {}\n", RequestBaseParam.getCostTime());
    }

    @AfterReturning(value = "pointCut(controllerHandler)", argNames = "controllerHandler,returnValue", returning = "returnValue")
    public void afterReturning(ControllerHandler controllerHandler, Object returnValue) throws JsonProcessingException {
        if(controllerHandler.printResponse()){
            logger.info("controller response is {}\n", objectMapper.writeValueAsString(returnValue));
        }
    }

}
