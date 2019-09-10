package com.jwzhu.platform.core.permission.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.util.StringUtil;
import com.jwzhu.platform.core.permission.bean.ResourceBean;
import com.jwzhu.platform.core.permission.model.ResourceType;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class ResourceAddParam extends BaseParam<ResourceBean> {

    @NotNull(message = "编码不能为空")
    private String code;
    private String parentCode;
    @NotEmpty(message = "名称不能为空")
    private String name;
    private String url;
    private Integer sort;
    @NotNull(message = "类型不能为空")
    private Short type;
    private Short menuShow;
    private Short resourceStatus;

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

    public Short getMenuShow() {
        return menuShow;
    }

    public void setMenuShow(Short menuShow) {
        this.menuShow = menuShow;
    }

    public Short getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(Short resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    @Override
    protected ResourceBean getBean() {
        return new ResourceBean();
    }

    @Override
    protected void exValid() {
        this.code = StringUtil.isEmpty(this.parentCode) ? this.code : this.parentCode + "." + this.code;
        if ((this.type == ResourceType.Page.getCode() || this.type == ResourceType.Function.getCode()) && StringUtil.isEmpty(this.url)) {
            throw new BusinessException("页面和功能的URL不能为空");
        }
    }
}
