package com.yling.hbase.controller;


import com.yling.hbase.pojo.ReturnMsgResult;
import com.yling.hbase.service.HbaseSearchI;
import com.yling.hbase.service.UserActionSearchI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author:yzm
 * @Description: 用户行为
 * @Date:2018/08/14 14:36
 * @Modified:
 */
@RestController
@RequestMapping("/useraction")
public class UserActionController {

    @Autowired
    private HbaseSearchI hbaseSearchI;

    @Autowired
    private UserActionSearchI userActionSearchI;

    /**
     * 分页查询用户行为记录(浏览、收藏)
     *
     * @param requestParams
     * @return
     */
    @RequestMapping(value = "/getUserActionRecordPage", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgResult getUserActionRecordPage(@RequestParam Map<String, Object> requestParams) {
        String topType = (String) (requestParams.get("topType"));             // browse浏览 store收藏
        String contentType = (String) requestParams.get("contentType");        //product共享 serviceInfo 资讯 activity 活动
        String contentId = (String) requestParams.get("contentId");            //内容id
        String accountId = (String) requestParams.get("userAccId");            //用户id
        String userType = (String) requestParams.get("userType");              // 10-普通 14-专家 0-游客
        if ("0".equals(userType) && StringUtils.isEmpty(accountId)) {   //如果查的是游客、且用户id为空
            accountId = "visitors-user";
            userType = null;
        }
        String startTime = (String) requestParams.get("startTime");            //20180815112203
        String endTime = (String) requestParams.get("endTime");
        Integer page = requestParams.get("page") == null ? 1 : Integer.valueOf(requestParams.get("page").toString());
        Integer limit = requestParams.get("limit") == null ? 10 : Integer.valueOf(requestParams.get("limit").toString());
        ReturnMsgResult returnMsgResult = hbaseSearchI.getUserActionRecordPage(topType, contentType, contentId, accountId, userType,
                startTime, endTime, page, limit);
        return returnMsgResult;
    }

    /**
     * 所有查询用户行为记录(浏览、收藏)
     *
     * @param requestParams
     * @return
     */
    @RequestMapping(value = "/getUserActionRecordAll", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgResult getUserActionRecordAll(@RequestParam Map<String, Object> requestParams) {
        String topType = (String) (requestParams.get("topType"));             // browse浏览 store收藏
        String contentType = (String) requestParams.get("contentType");        //product共享 serviceInfo 资讯 activity 活动
        String contentId = (String) requestParams.get("contentId");            //内容id
        String accountId = (String) requestParams.get("userAccId");            //用户id
        String userType = (String) requestParams.get("userType");              // 10-普通 14-专家 0-游客
        if ("0".equals(userType) && StringUtils.isEmpty(accountId)) {   //如果查的是游客、且用户id为空
            accountId = "visitors-user";
            userType = null;
        }
        String startTime = (String) requestParams.get("startTime");            //20180815112203
        String endTime = (String) requestParams.get("endTime");
        ReturnMsgResult returnMsgResult = hbaseSearchI.getUserActionRecordAll(topType, contentType, contentId, accountId, userType,
                startTime, endTime);
        return returnMsgResult;
    }

    /**
     * 查询用户行为记录(浏览、收藏)
     *
     * @param requestParams
     * @return
     */
    @RequestMapping(value = "/userOperSummary", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getUserActionOperSummary(@RequestParam Map<String, Object> requestParams) {
        String startTime = (String) (requestParams.get("startTime"));
        String endTime = (String) requestParams.get("endTime");
        String queryType = (String) requestParams.get("queryType");
        String contentType = (String) requestParams.get("contentType");
        List<String> list = userActionSearchI.getUserActionOperSummary(startTime, endTime, queryType, contentType);
        return list;
    }

    /**
     * 查询用户行为记录(浏览、收藏)
     *
     * @param requestParams
     * @return
     */
    @RequestMapping(value = "/userActionCount", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgResult getUserActionCount(@RequestParam Map<String, Object> requestParams) {
        String queryType = (String) requestParams.get("queryType");
        Integer page = requestParams.get("page") == null ? 1 : Integer.valueOf(requestParams.get("page").toString());
        Integer limit = requestParams.get("limit") == null ? 10 : Integer.valueOf(requestParams.get("limit").toString());
        ReturnMsgResult returnMsgResult = userActionSearchI.getUserActionCount(queryType, page, limit);
        return returnMsgResult;
    }
}
