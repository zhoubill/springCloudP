package com.yling.hbase.pojo;



/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/8/16 22:38
 * @Modified:
 */
public class UserImageSex {
    public String sex;
    public Integer total;

    public UserImageSex() {
    }

    public String getSex() {
        return sex == null ? "":sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "UserImageSex{" +
                "sex='" + sex + '\'' +
                ", total=" + total +
                '}';
    }
}
