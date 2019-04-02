package com.jwzhu.platform.core.resource.param;

import javax.validation.constraints.NotNull;

import com.jwzhu.platform.core.resource.bean.ResourceBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class UpdateResourceParam extends BaseParam<ResourceBean> {

    @NotNull(message = "ID不能为空")
    private Long id;
    private String name;
    private String url;
    private Integer sort;
    private Short type;
    private Short isShow;
    private Short availableStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
