package com.yling.hive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yling.hive.pojo.ReturnMsgResult;
import com.yling.hive.service.BusStatisticsSearchByHiveI;

/**
 * @Author:ZhouZhou
 * @Description:公交出行相关接口
 * @Date:2018/10/22 9:54
 * @Modified:
 */
@RestController
@RequestMapping("/busStatic")
public class BusStatisticsController {

	@Autowired
	private BusStatisticsSearchByHiveI busStatisticsSearchByHiveI;

	/*
	 * 公交车出行按小时统计查询
	 *
	 */
	@RequestMapping(value = "/getBusStatistics", method = RequestMethod.POST)
	@ResponseBody
	public ReturnMsgResult getUserImageAge() {
		return busStatisticsSearchByHiveI.getBusStatistics();
	}

}
