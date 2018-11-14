package com.yling.hbase.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yling.hbase.pojo.ReturnMsgResult;
import com.yling.hbase.service.UserImageSearchI;

/**
 * @Author:WangKeSheng
 * @Description:用户画像相关接口
 * @Date:2018/8/16 22:34
 * @Modified:
 */
@RestController
@RequestMapping("/userimage")
public class UserImageController {

    @Autowired
    private UserImageSearchI userImageSearchI;

//    @Autowired
//    private UserImageSearchByHiveI userImageSearchByHiveI;

    /*
    * 用户画像年龄分布查询
    *
    * */
    @RequestMapping(value = "/getUserImageAge", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgResult getUserImageAge(){
        return userImageSearchI.getUserImageAge();
    }

    /*
    * 用户画像性别分布查询
    *
    * */
    @RequestMapping(value = "/getUserImageSex", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgResult getUserImageSex(){
        return userImageSearchI.getUserImageSex();
    }

    /*
    * 用户画像地域分布查询
    *
    * */
    @RequestMapping(value = "/getUserImageArea", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgResult getUserImageArea(){
        return userImageSearchI.getUserImageArea();
    }

    /*
    * 用户画像
    *
    * */
//    @RequestMapping(value = "/getUserImage", method = RequestMethod.POST)
//    @ResponseBody
//    public ReturnMsgResult getUserImage(){
//        return userImageSearchByHiveI.getUserImage();
//    }
}
