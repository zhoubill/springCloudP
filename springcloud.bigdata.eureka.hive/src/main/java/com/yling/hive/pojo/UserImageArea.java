package com.yling.hive.pojo;



/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/8/16 22:38
 * @Modified:
 */
public class UserImageArea {
    public String provinceName;
    public Integer total;

    public String getProvinceName() {
        return provinceName == null ? "":provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public UserImageArea() {
    }

    public UserImageArea(String provinceName, Integer total) {
        this.provinceName = provinceName;
        this.total = total;
    }

    @Override
    public String toString() {
        return "UserImageArea{" +
                "provinceName='" + provinceName + '\'' +
                ", total=" + total +
                '}';
    }
}
