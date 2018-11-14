package com.yling.hive.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.yling.hive.dao.BusStatisticsDao;
import com.yling.hive.pojo.BusStatistics;
import com.yling.hive.pojo.ReturnMsgResult;
import com.yling.hive.service.BusStatisticsSearchByHiveI;

/**
 * @Author:ZhouZhou
 * @Description:
 * @Date:2018/10/22 9:56
 * @Modified:
 */
@Service
public class BusStatisticsSearchByHiveImpl implements BusStatisticsSearchByHiveI {

	@Autowired
	private BusStatisticsDao busStatisticsDao;

	@Override
	public ReturnMsgResult getBusStatistics() {
		String code = "000";
		String msg = "";
		List<BusStatistics> busStatisticsList = busStatisticsDao.queryBusStatistics();
		Map map = new HashMap();
		map.put("BusStatistics", busStatisticsList);
		ReturnMsgResult returnMsgResult = new ReturnMsgResult();
		returnMsgResult.setCode(code);
		returnMsgResult.setMsg(msg);
		returnMsgResult.setResult(map);
		return returnMsgResult;
	}

}
