package com.yling.hive.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yling.hive.pojo.BusStatistics;



/**
 * @Author:ZhouZhou
 * @Description:
 * @Date:2018/10/22 9:18
 * @Modified:
 */

@Repository
public class BusStatisticsDao {

	@Autowired
	@Qualifier("hiveJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	 public List<BusStatistics> queryBusStatistics(){
	        String sql = "SELECT bussitename,alltotal,numdays,buslocation,consumehour,sitelat,sitelon FROM ecard_bus_site_statistics";
	        RowMapper<BusStatistics> rowMapper = new BeanPropertyRowMapper<>(BusStatistics.class);
	        List<BusStatistics> busStatisticsList = jdbcTemplate.query(sql, rowMapper);
	        return busStatisticsList;
	    }

}
