package com.jwzhu.platform.core.resource.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.core.resource.bean.ResourceBean;
import com.jwzhu.platform.core.resource.model.ResourceType;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class AddResourceParam extends BaseParam<ResourceBean> {

    @NotNull(message = "编码不能为空")
    private String code;
    private String parentCode;
    @NotEmpty(message = "名称不能为空")
    private String name;
    private String url;
    private Integer sort;
    @NotEmpty(message = "类型不能为空")
    private Short type;
    private Short isShow;
    private Short availableStatus;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getIsShow() {
        return isShow;
    }

    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    public Short getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(Short availableStatus) {
        this.availableStatus = availableStatus;
    }

    @Override
    protected ResourceBean getBean() {
        return new ResourceBean();
    }

    @Override
    protected void exValid() {
        if((this.type == ResourceType.Page.getCode() || this.type == ResourceType.Function.getCode()) && StringUtils.isEmpty(this.url)){
            throw new BusinessException("页面和功能的URL不能为空");
        }
    }
}
