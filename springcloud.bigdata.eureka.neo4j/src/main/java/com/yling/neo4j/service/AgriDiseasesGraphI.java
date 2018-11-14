package com.yling.neo4j.service;

import com.yling.neo4j.pojo.ReturnMsgResult;

public interface AgriDiseasesGraphI {
	
	/**
	 * 获取农业数据分类图谱
	 * @return
	 */
	public ReturnMsgResult getDataClassificationGraph(String deeplevel);
	
	/**
	 * 根据病虫害名字关联获取相关因素
	 * @param diseaseName
	 * @return
	 */
	public ReturnMsgResult getAgriDiseasesAssociateFactor(String diseaseName);

}
