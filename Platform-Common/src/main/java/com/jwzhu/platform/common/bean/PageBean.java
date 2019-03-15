package com.jwzhu.platform.common.bean;

import java.util.List;

import com.jwzhu.platform.common.enums.YesOrNo;

/**
 * 分页参数类
 */
public class PageBean<T> extends BaseBean{

    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 页大小
     */
    private Integer pageSize;
    /**
     * 结果集
     */
    private List<T> list;
    /**
     * 是否需要分页
     */
    private Short needCut;
    /**
     * 结果总数
     */
    private Integer totalCount;

    /**
     * 是否分页
     */
    public boolean needCut() {
        return YesOrNo.Yes.getCode() == this.needCut;
    }

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

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
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
        if(this.totalCount != null){
            this.totalPage = this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize : this.totalCount / this.pageSize + 1;
        }
    }
}
