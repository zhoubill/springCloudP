package com.yling.hive.pojo;



/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/8/16 22:38
 * @Modified:
 */
public class UserImageAge {
    public Integer age;
    public Integer total;

    public UserImageAge() {
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "UserImageAge{" +
                "age=" + age +
                ", total=" + total +
                '}';
    }
}
