package com.jwzhu.platform.web.base.param;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.BeanUtils;

import com.jwzhu.platform.common.bean.BaseBean;
import com.jwzhu.platform.common.exception.ParamException;

public abstract class BaseParam<T extends BaseBean> {
    /**
     * 访问令牌
     */
    private String token;
    /**
     * 请求类型
     */
    private Short requestType;

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Short getRequestType() {
        return requestType;
    }

    public void setRequestType(Short requestType) {
        this.requestType = requestType;
    }

    protected abstract T getBean();

    public String[] getIgnoreProperties() {
        return null;
    }

    public T initBean() {
        T target = getBean();
        if (target != null) {
            BeanUtils.copyProperties(this, target, getIgnoreProperties());
        }
        return target;
    }

    public void valid(Class... groups) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BaseParam<T>>> constraintViolations = validator.validate(this, groups);

        if(!constraintViolations.isEmpty()){
            ConstraintViolation<BaseParam<T>> constraintViolation = constraintViolations.iterator().next();
            throw new ParamException(constraintViolation.getMessage());
        }
    }

}
