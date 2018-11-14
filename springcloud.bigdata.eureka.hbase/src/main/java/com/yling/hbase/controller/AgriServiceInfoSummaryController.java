package com.yling.hbase.controller;


import com.yling.hbase.service.HbaseSearchI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author:yzm
 * @Description:
 * @Date:2018/08/14 14:36
 * @Modified:
 */
@RestController
@RequestMapping("/agriserviceinfo")
public class AgriServiceInfoSummaryController {

    @Autowired
    private HbaseSearchI hbaseSearchI;


    /**
     * 资讯统计
     * @param requestParams
     * @return
     */
    @RequestMapping(value = "/serviceSummary", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getAgriServiceInfoSummary(@RequestParam Map<String, Object> requestParams) {
        String startTime = (String) requestParams.get("startTime");
        String endTime = (String) requestParams.get("endTime");
        String type = (String) requestParams.get("type");
        List<String> searchResultList = hbaseSearchI.getAgriServiceInfoSummary(startTime, endTime, type);
        return searchResultList;

    }

}
