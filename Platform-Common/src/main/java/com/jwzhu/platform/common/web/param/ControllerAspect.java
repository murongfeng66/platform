package com.jwzhu.platform.common.web.param;

import javax.servlet.http.HttpServletRequest;

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
import com.jwzhu.platform.common.web.exception.JsonException;
import com.jwzhu.platform.common.web.exception.PageException;
import com.jwzhu.platform.common.web.request.RequestBaseParam;
import com.jwzhu.platform.common.web.request.RequestType;
import com.jwzhu.platform.common.web.request.RequestUtil;
import com.jwzhu.platform.common.web.response.WebResult;
import com.jwzhu.platform.common.web.token.TokenService;
import com.jwzhu.platform.common.web.token.TokenSubject;

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
        logger.info("controller aspect point is {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
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

        analyzeToken();
    }

    private void analyzeToken() {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request == null) {
            return;
        }
        String token = request.getHeader(tokenService.getTokenConfig().getParamName());
        String paramToken = request.getParameter(tokenService.getTokenConfig().getParamName());
        token = StringUtils.isEmpty(token) && !StringUtils.isEmpty(paramToken) ? paramToken : token;
        if(!StringUtils.isEmpty(token)){
            TokenSubject subject = tokenService.analyzeToken(token);
            RequestBaseParam.setRequestUser(subject);
        }
    }

    @AfterThrowing(value = "pointCut(controllerHandler)", argNames = "e,controllerHandler", throwing = "e")
    public void afterThrowing(Throwable e, ControllerHandler controllerHandler) {
        logger.error("controller end with a exception\n");
        if (RequestBaseParam.getRequestType() == RequestType.Ajax) {
            throw new JsonException(e);
        } else {
            throw new PageException(e);
        }
    }

    @After(value = "pointCut(controllerHandler)", argNames = "controllerHandler")
    public void after(ControllerHandler controllerHandler) {
        RequestBaseParam.initResponseTime();
        logger.error("controller end and costTime is {}", RequestBaseParam.getCostTime());
    }

    @AfterReturning(value = "pointCut(controllerHandler)", argNames = "controllerHandler,returning", returning = "returning")
    public void afterReturning(ControllerHandler controllerHandler, Object returning) throws JsonProcessingException {
        if (returning instanceof WebResult) {
            WebResult webResult = (WebResult) returning;
            webResult.setCostTime(RequestBaseParam.getCostTime());
        }
        logger.info("controller response is " + objectMapper.writeValueAsString(returning) + "\n");
    }

}
