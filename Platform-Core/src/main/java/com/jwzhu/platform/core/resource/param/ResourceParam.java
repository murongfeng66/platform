package com.jwzhu.platform.core.resource.param;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.jwzhu.platform.web.base.param.BaseParam;
import com.jwzhu.platform.core.resource.bean.ResourceBean;

public class ResourceParam extends BaseParam<ResourceBean> {

    private Long id;
    @NotNull(message = "编码不能为空")
    private String code;
    private String parentCode;
    private String name;
    private String url;
    private Integer sort;
    private Short type;
    private Short isShow;
    private Timestamp createTime;
    private Timestamp updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected ResourceBean getBean() {
        return new ResourceBean();
    }
}
