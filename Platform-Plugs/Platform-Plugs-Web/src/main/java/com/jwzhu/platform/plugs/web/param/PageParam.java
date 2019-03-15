package com.jwzhu.platform.plugs.web.param;

import com.jwzhu.platform.common.bean.BaseBean;

/**
 * 分页参数类
 */
public abstract class PageParam<T extends BaseBean> extends BaseParam<T> {

    /**
     * 当前页
     */
    private Integer currentPage = 1;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 页大小
     */
    private Integer pageSize = 10;
    /**
     * 是否需要分页
     */
    private Short needCut = 1;
    /**
     * 结果总数
     */
    private Integer totalCount;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Short getNeedCut() {
        return needCut;
    }

    public void setNeedCut(Short needCut) {
        this.needCut = needCut;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
