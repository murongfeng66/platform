package com.jwzhu.platform.plugs.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.exception.NoPermissionException;
import com.jwzhu.platform.common.exception.ParamException;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.plugs.web.exception.token.TokenEmptyException;
import com.jwzhu.platform.plugs.web.exception.token.TokenErrorException;
import com.jwzhu.platform.plugs.web.exception.token.TokenTimeOutException;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;
import com.jwzhu.platform.plugs.web.response.ResponseCode;
import com.jwzhu.platform.plugs.web.response.WebResult;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = JsonException.class)
    @ResponseBody
    public WebResult<String> JsonExceptionHandler(JsonException e) {
        WebResult<String> result = new WebResult<>();
        Throwable throwable = e.getE();
        ResponseCode responseCode = ResponseCode.get(e.getE());
        result.setCode(responseCode.getCode());
        result.setMessage(getErrorMessage(throwable));
        result.setCostTime(RequestBaseParam.getCostTime());
        if(throwable instanceof TokenErrorException || throwable instanceof TokenEmptyException || throwable instanceof TokenTimeOutException){
            result.setRedirect("/");
        }
        return result;
    }

    @ExceptionHandler(value = PageException.class)
    public ModelAndView PageExceptionHandler(PageException e) {
        ModelAndView view = new ModelAndView();
        Throwable throwable = e.getE();
        ResponseCode responseCode = ResponseCode.get(e.getE());
        view.addObject("responseCode", responseCode.getCode());
        view.addObject("message", getErrorMessage(throwable));
        view.addObject("costTime", RequestBaseParam.getCostTime());
        view.setViewName("/error");
        return view;
    }

    private String getErrorMessage(Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
        if (throwable instanceof SystemException) {
            return "系统异常";
        } else if (throwable instanceof BusinessException || throwable instanceof ParamException || throwable instanceof TokenErrorException || throwable instanceof NoPermissionException || throwable instanceof TokenEmptyException || throwable instanceof TokenTimeOutException) {
            return throwable.getMessage();
        }
        return "系统错误";
    }

}