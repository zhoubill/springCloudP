package com.yling.hanlp.service;

import com.yling.hanlp.pojo.ReturnMsgResult;

/**
 * @Author:zhouzhou
 * @Description:
 * @Date:2018/8/19 9:56
 * @Modified:
 */
public interface AgriConsultHotWordI {

	/*
	 * 获取农高会用户咨询问题分词
	 */
	ReturnMsgResult getAgriConsultHotWord(String id);

}
