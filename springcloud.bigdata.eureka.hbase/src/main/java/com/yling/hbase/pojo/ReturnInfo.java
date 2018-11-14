package com.yling.hbase.pojo;



import java.util.List;

/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/5/10 11:40
 * @Modified:
 */
public class ReturnInfo {
    private long total;
    private List rows;

    public ReturnInfo() {
    }

    public ReturnInfo(int total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "AccountFront{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
