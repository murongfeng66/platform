package com.jwzhu.platform.file.param;

import javax.validation.constraints.NotEmpty;

import com.jwzhu.platform.file.bean.RegionBean;
import com.jwzhu.platform.plugs.web.param.BaseParam;

public class RegionAddParam extends BaseParam<RegionBean> {

    @NotEmpty(message = "文件域名称不能为空")
    private String regionName;
    @NotEmpty(message = "文件域路径不能为空")
    private String path;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    protected RegionBean getBean() {
        return new RegionBean();
    }
}
