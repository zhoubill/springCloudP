package com.yling.hbase.pojo;



/**
 * @Author:yzm
 * @Description: 用户行为记录
 * @Date:2018/8/15 11:21
 * @Modified:
 */
public class UserActionRecord {

    // 浏览 收藏
    public String topType;

    // 资讯 活动 共享
    public String contentType;

    //内容id
    public String contentId;

    //用户id
    public String userAccId;

    //用户类型 0-游客 10-普通 14-专家
    public String userType;

    //时间
    public String requestTime;


}
