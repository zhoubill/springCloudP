package com.yling.hbase.controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yling.hbase.service.HbaseSearchI;

/**
 * @Author:zhouzhou
 * @Description:
 * @Date:2018/08/07 14:36
 * @Modified:
 */
@RestController
@RequestMapping("/agriproducrt")
public class AgricultureProductSummaryController {
	
	@Autowired
	private HbaseSearchI hbaseSearchI;

	
	@RequestMapping(value = "/summary", method = RequestMethod.POST)
	@ResponseBody  
	public List<String> getAgriProducrtSummary(@RequestParam Map<String, Object> requestParams){
		String startTime = (String) requestParams.get("startTime");
		String endTime = (String) requestParams.get("endTime");
		List<String> searchResultList = hbaseSearchI.getAgricultureProductSummary(startTime, endTime);
		return searchResultList;
		
	}

}
