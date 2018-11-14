package com.yling.hbase.service;

import com.yling.hbase.pojo.ReturnMsgResult;

/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/8/16 22:37
 * @Modified:
 */
public interface UserImageSearchI {
    /*
    * 用户画像年龄画像
    * */
    ReturnMsgResult getUserImageAge();
    /*
    * 用户画像年龄性别
    * */
    ReturnMsgResult getUserImageSex();
    /*
    * 用户画像年龄性别
    * */
    ReturnMsgResult getUserImageArea();

    /*
    * 用户画像
    * */
    ReturnMsgResult getUserImage();
}
