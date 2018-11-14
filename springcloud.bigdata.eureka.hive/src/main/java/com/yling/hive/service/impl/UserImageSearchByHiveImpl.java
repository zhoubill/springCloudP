package com.yling.hive.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yling.hive.dao.UserImageDao;
import com.yling.hive.pojo.ReturnMsgResult;
import com.yling.hive.pojo.UserImageAge;
import com.yling.hive.pojo.UserImageArea;
import com.yling.hive.pojo.UserImageSex;
import com.yling.hive.service.UserImageSearchByHiveI;

/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/8/19 9:56
 * @Modified:
 */
@Service
public class UserImageSearchByHiveImpl implements UserImageSearchByHiveI {
    @Autowired
    private UserImageDao userImageDao;
    @Override
    public ReturnMsgResult getUserImage() {
        String code = "000";
        String msg = "";
        List<UserImageArea> userImageAreaList = userImageDao.queryUserImageArea();

        List<UserImageSex> userImageSexList = userImageDao.queryUserImageSex();

        List<UserImageAge> userImageAgeList = userImageDao.queryUserImageAge();

        Map map = new HashMap();
        map.put("UserImageAge",userImageAgeList);
        map.put("UserImageSex",userImageSexList);
        map.put("UserImageArea",userImageAreaList);

        ReturnMsgResult returnMsgResult = new ReturnMsgResult();
        returnMsgResult.setCode(code);
        returnMsgResult.setMsg(msg);
        returnMsgResult.setResult(map);
        return returnMsgResult;
    }
}
