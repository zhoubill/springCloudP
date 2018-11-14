package com.yling.hbase.service;



import java.util.List;

import com.yling.hbase.pojo.ReturnMsgResult;

public interface HbaseSearchI {

	/**
	 *从hbase中获取资源统计的结果
	 * @author zhouzhou
	 * @param  startTime
	 * @param  endTime
	 */
 	public List<String>  getAgricultureProductSummary(String startTime, String endTime);

	/**
	 *
	 * @param startTime 开始时间
	 * @param endTime   结束时间
     * @return
     */
	public List<String>  getActivitySummary(String startTime, String endTime);

    /**
     * 资讯统计
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param type      类型
     * @return
     */
    public List<String>  getAgriServiceInfoSummary(String startTime, String endTime,String type);

	/**
	 *从hbase中获取资源统计的结果
	 * @author renxuanxuan
	 * @param  startTime
	 * @param  endTime
	 */
	public List<String>  getMallOrderSummary(String startTime, String endTime);

	/**
	 * 分页查询用户行为记录(浏览、收藏)
	 * @param topType  browse浏览 store收藏
	 * @param contentType  product共享 serviceInfo 资讯 activity 活动
	 * @param contentId
	 * @param accountId
	 * @param userType 10-普通 14-专家 0-游客
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param limit
	 */
	ReturnMsgResult getUserActionRecordPage(String topType, String contentType, String contentId, String accountId, String userType,
										String startTime, String endTime, Integer page, Integer limit);

	/**
	 * 所有查询用户行为记录(浏览、收藏)
	 * @param topType  browse浏览 store收藏
	 * @param contentType  product共享 serviceInfo 资讯 activity 活动
	 * @param contentId
	 * @param accountId
	 * @param userType 10-普通 14-专家 0-游客
	 * @param startTime
	 * @param endTime
	 */
	ReturnMsgResult getUserActionRecordAll(String topType, String contentType, String contentId, String accountId, String userType,
											String startTime, String endTime);

}
