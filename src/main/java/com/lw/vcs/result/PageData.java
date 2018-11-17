package com.lw.vcs.result;

/**
 * @Author：lian.wei
 * @Date：2018/9/30 13:03
 * @Description：
 */
public class PageData {
    private Integer page;
    private Integer pageSize;
    private long total;
    private Object rows;

    public static PageData create() {
        return new PageData();
    }
    public Integer getPage() {
        return page;
    }

    public PageData setPage(Integer page) {
        this.page = page;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public PageData setTotal(long total) {
        this.total = total;
        return this;
    }

    public Object getRows() {
        return rows;
    }

    public PageData setRows(Object rows) {
        this.rows = rows;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public PageData setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
