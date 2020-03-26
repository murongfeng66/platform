package com.jwzhu.platform.plugs.web.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.jwzhu.platform.common.SystemConfig;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.exception.NoPermissionException;
import com.jwzhu.platform.common.exception.ParamException;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.util.StringUtil;
import com.jwzhu.platform.common.web.RequestInfo;
import com.jwzhu.platform.plugs.web.exception.token.TokenEmptyException;
import com.jwzhu.platform.plugs.web.exception.token.TokenErrorException;
import com.jwzhu.platform.plugs.web.exception.token.TokenTimeOutException;
import com.jwzhu.platform.plugs.web.response.ResponseCode;
import com.jwzhu.platform.plugs.web.response.WebResult;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Autowired
    private SystemConfig systemConfig;

    @ExceptionHandler(value = JsonException.class)
    @ResponseBody
    public WebResult<String> JsonExceptionHandler(JsonException e) {
        WebResult<String> result = new WebResult<>();
        Throwable throwable = e.getE();
        logger.error(throwable.getMessage(), throwable);
        ResponseCode responseCode = ResponseCode.get(e.getE());
        result.setCode(responseCode.getCode());
        result.setMessage(getErrorMessage(throwable));
        result.setCostTime(RequestInfo.getCostTime());
        if (throwable instanceof TokenErrorException || throwable instanceof TokenEmptyException || throwable instanceof TokenTimeOutException) {
            result.setRedirect(systemConfig.getMain().getHost());
        }
        return result;
    }

    @ExceptionHandler(value = PageException.class)
    public ModelAndView PageExceptionHandler(PageException e, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        Throwable throwable = e.getE();
        logger.error(throwable.getMessage(), throwable);
        if (throwable instanceof TokenEmptyException || throwable instanceof TokenTimeOutException) {
            String redirect = "/";
            if (systemConfig.getSub() != null && !StringUtil.isEmpty(systemConfig.getSub().getHost())) {
                UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(systemConfig.getMain().getCheckLogin());
                uriComponentsBuilder.queryParam("originUrl", request.getRequestURL().toString());
                uriComponentsBuilder.queryParam("subHost", systemConfig.getSub().getHost());
                redirect = uriComponentsBuilder.toUriString();
            }
            view.setViewName("redirect:" + redirect);
        } else if (throwable instanceof TokenErrorException) {
            view.setViewName("redirect:" + systemConfig.getMain().getHost());
        } else {
            ResponseCode responseCode = ResponseCode.get(e.getE());
            view.addObject("responseCode", responseCode.getCode());
            view.addObject("message", getErrorMessage(throwable));
            view.addObject("costTime", RequestInfo.getCostTime());
            view.setViewName("error");
        }
        return view;
    }

    private String getErrorMessage(Throwable throwable) {
        if (throwable instanceof SystemException) {
            return "系统异常";
        } else if (throwable instanceof BusinessException || throwable instanceof ParamException || throwable instanceof TokenErrorException || throwable instanceof NoPermissionException || throwable instanceof TokenEmptyException || throwable instanceof TokenTimeOutException) {
            return throwable.getMessage();
        }
        return "系统错误";
    }

}