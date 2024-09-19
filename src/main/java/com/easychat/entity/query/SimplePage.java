package com.easychat.entity.query;

import com.easychat.enums.PageSize;

public class SimplePage {

    private int pageNo;
    private int pageSize;
    private int countTotal;
    private int countPage;
    private int start;
    private int end;

    public SimplePage() {
    }

    public SimplePage(Integer pageNo, Integer pageSize, int countTotal) {
        if (null == pageNo || pageNo < 1) {
            pageNo = 1;
        }
        if (null == pageSize || pageSize <= 0) {
            pageSize = PageSize.SIZE_10.getSize();
        }
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.countTotal = countTotal;
        action();
    }

    public SimplePage(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public void action() {
        if (countTotal > 0) {
            countPage = countTotal % pageSize == 0 ? countTotal / pageSize : (countTotal / pageSize) + 1;
        } else {
            countPage = 1;
        }
        if (pageNo > countPage) {
            pageNo = countPage;
        }
        start = (pageNo - 1) * pageSize;
        end = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
        action();
    }

    public int getCountPage() {
        return countPage;
    }

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
