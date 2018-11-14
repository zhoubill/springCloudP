package com.yling.hbase.service;



import java.util.List;

import com.yling.hbase.pojo.ReturnMsgResult;

/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/8/15 17:13
 * @Modified:
 */
public interface UserActionSearchI {
    /**
     *从hbase中获取资源统计的结果
     * @author WangKeSheng
     * @param  startTime 开始时间
     * @param  endTime 结束时间
     * @param  queryType 浏览 或 收藏
     */
    List<String> getUserActionOperSummary(String startTime, String endTime,String queryType,String contentType);

    ReturnMsgResult getUserActionCount(String queryType,Integer page, Integer limit);

}
