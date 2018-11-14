package com.yling.es.service;


import org.elasticsearch.search.SearchHits;

import com.yling.es.pojo.ReturnMsgResult;

import java.util.List;
import java.util.Map;

public interface EsSearchI {
	public List<String> getResultByFunctionScore(String index, String type, Map<String, String> filterParams,
												 Map<String, Object> sortParams, int begin, int size);

	public int searchResultByMatchAll(String index, String type);
	//推荐排序
	ReturnMsgResult getResultForSort(String index, String type, List<String> sortParams,
									 String eClassCode,String rDirectCode,String orgID,int begin, int size);
	//默认排序
	ReturnMsgResult getResultForDefaultSort(String index, String type, Map<String, String> filterParams,
											Map<String, Object> sortParams,
											String eClassCode,String rDirectCode,String orgID,int begin, int size);
	//距离排序
	ReturnMsgResult getResultForLocationSort(String index, String type, Map<String, String> filterParams,
											 Map<String, Object> sortParams,
											 String eClassCode,String rDirectCode,String orgID,int begin, int size);

	//病虫害
	ReturnMsgResult getPest(String index, String type, Map<String, String> filterParams,
							String content,Map<String, Object> sortParams,int begin, int size);
}
