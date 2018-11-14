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
import com.yling.hbase.utils.ConfigConstant;

/**
 * @Author:zhouzhou
 * @Description:
 * @Date:2018/08/07 14:36
 * @Modified:
 */
@RestController
@RequestMapping("/agriactivity")
public class AgricultureActivitySummaryController {

	@Autowired
	private HbaseSearchI hbaseSearchI;
	
	@Autowired
	private ConfigConstant configConstant;

	@RequestMapping(value = "/activitysummary", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getAgriActivitySummary(@RequestParam Map<String, Object> requestParams) {
		String startTime = (String) requestParams.get("startTime");
		String endTime = (String) requestParams.get("endTime");
		List<String> searchResultList = hbaseSearchI.getActivitySummary(startTime, endTime);
		return searchResultList;

	}
	
	@RequestMapping(value = "/hello")
	public String hello() {
		return configConstant.getHbaseHost();
	}

}
