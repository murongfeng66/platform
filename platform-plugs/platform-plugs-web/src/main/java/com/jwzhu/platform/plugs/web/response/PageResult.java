package com.jwzhu.platform.plugs.web.response;

import java.util.List;

/**
 * 分页参数类
 */
public class PageResult<T> extends WebResult<List<T>> {

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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
