package com.yling.recommend.service;

import com.yling.recommend.pojo.ReturnMsgResult;

/**
 * 人才推荐的的服务类
 * 
 * @author zhouzhou
 *
 */
public interface TalentSupplyRecommendI {

	/**
	 * 根据传进来的人才供需ID和type类型获取推荐的ID
	 * @param ID
	 * @param type
	 * @return
	 */
	public ReturnMsgResult getSupplyOrTalentRecommendByTypeAndId(String ID, String type);

}
