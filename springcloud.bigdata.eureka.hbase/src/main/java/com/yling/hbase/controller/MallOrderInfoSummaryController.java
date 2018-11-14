package com.yling.hbase.controller;

import com.yling.hbase.service.HbaseSearchI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description:交易统计
 * @Author:renxuanxuan
 * @Date:2018/8/14 14:08
 */
@RestController
@RequestMapping("/mallorder")
public class MallOrderInfoSummaryController {
    @Autowired
    private HbaseSearchI hbaseSearchI;

    /**
     * 交易统计
     * @param requestParams
     * @return
     */
    @RequestMapping(value = "/mallSummary", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getMallOrderInfoSummary(@RequestParam Map<String, Object> requestParams) {
        String startTime = (String) requestParams.get("startTime");
        String endTime = (String) requestParams.get("endTime");
        List<String> searchResultList = hbaseSearchI.getMallOrderSummary(startTime, endTime);
        return searchResultList;

    }

}
