package com.yling.es.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.yling.es.pojo.ExpertInfoFromEs;
import com.yling.es.pojo.PestInfoFromEs;
import com.yling.es.pojo.ReturnInfo;
import com.yling.es.pojo.ReturnMsgResult;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yling.es.service.EsSearchI;
import com.yling.es.utils.EsClinet;

import net.sf.json.JSONObject;

@Service
public class EsSearchImpl implements EsSearchI {
	
	@Autowired
	public EsClinet esClinet;

	/**
	 * 根据native自定义排序插件进行es的查询
	 *
	 * @param index
	 * @param type
	 * @param filterMustParams
	 * @param sortParams
	 */
	public List<String> getResultByFunctionScore(String index, String type, Map<String, String> filterMustParams,
												 Map<String, Object> sortParams, int begin, int size) {
		List<String> SearchResultList = new ArrayList<String>();
		BoolQueryBuilder filterQueryBulider = QueryBuilders.boolQuery();
		for (Map.Entry<String, String> entry : filterMustParams.entrySet()) {
			String filterFiled = entry.getValue();
			String filtercondition = entry.getKey();
			MatchPhraseQueryBuilder mpq = QueryBuilders.matchPhraseQuery(filterFiled, filtercondition);
			filterQueryBulider.must(mpq);
		}

		filterQueryBulider.mustNot(QueryBuilders.existsQuery("status"));

		Script script = new Script(ScriptType.INLINE, "native", "my_script", sortParams);
		ScriptScoreFunctionBuilder scriptBuilder = ScoreFunctionBuilders.scriptFunction(script);
		SearchRequestBuilder requestBuilder = esClinet.getEsclinet().prepareSearch(index).setTypes(type)
				.setQuery(new FunctionScoreQueryBuilder(filterQueryBulider, scriptBuilder));
		SearchResponse response = requestBuilder.setFrom(begin).setSize(size).execute().actionGet();
		SearchHit[] hits = response.getHits().getHits();
		for (int j = 0; j < hits.length; j++) {
			SearchResultList.add(hits[j].getSourceAsString());
		}
		return SearchResultList;

	}

	/**
	 * 根据给定的索引获取整个type类型下的文档
	 *
	 * @param index
	 * @param type
	 * @param begin
	 * @param size
	 * @return
	 * @throws ParseException
	 */
	public int searchResultByMatchAll(String index, String type) {
		String response = esClinet.getEsclinet().prepareSearch(index).setTypes(type).setSize(0).execute()
				.actionGet().toString();
		JSONObject JsonResponse = JSONObject.fromObject(response);
		JSONObject JsonResponse_hits = JSONObject.fromObject(JsonResponse.get("hits"));
		String count_num = JsonResponse_hits.get("total").toString();
		return Integer.valueOf(count_num);
	}

	/**
	 * 根据给定的索引获取整个type类型下的文档排序后分页提取 对应app中推荐排序
	 *
	 * @param index
	 * @param type
	 * @param begin
	 * @param size
	 * @param sortParams
	 * @param keyWord
	 * @return
	 * @throws ParseException
	 */
	public ReturnMsgResult getResultForSort(String index, String type, List<String> sortParams,
											String eClassCode,String rDirectCode,String orgID,int begin, int size) {
		List<ExpertInfoFromEs> expertInfoFromEsList = new ArrayList<ExpertInfoFromEs>();
		ReturnMsgResult returnMsgResult = new ReturnMsgResult();
		ReturnInfo returnInfo = new ReturnInfo();
		String code = "000";
		String msg = "";
		SearchRequestBuilder searchRequestBuilder = esClinet
				.getEsclinet()
				.prepareSearch(index)
				.setTypes(type)
				.setFrom(begin)
				.setSize(size);
		BoolQueryBuilder boolBuilder = null;
		if(StringUtils.isNotEmpty(eClassCode) || StringUtils.isNotEmpty(rDirectCode) ||
				StringUtils.isNotEmpty(orgID)){
			//建立bool查询
			boolBuilder = QueryBuilders.boolQuery();
			//使用should实现或者查询
			if(StringUtils.isNotEmpty(eClassCode)){
				//boolBuilder.must(QueryBuilders.matchPhraseQuery("typeCode",eClassCode));
				boolBuilder.must(QueryBuilders.wildcardQuery("typeCode",("*"+eClassCode+"*")));

			}
			if(StringUtils.isNotEmpty(rDirectCode)){
				//boolBuilder.must(QueryBuilders.matchPhraseQuery("rdcode",rDirectCode));
				boolBuilder.must(QueryBuilders.wildcardQuery("rdcode",("*"+rDirectCode+"*")));
			}
			if(StringUtils.isNotEmpty(orgID)){
				boolBuilder.must(QueryBuilders.matchQuery("orginationID",orgID));
			}
			boolBuilder.must(QueryBuilders.matchQuery("isuse","1"));
		}
		if(boolBuilder != null){
			searchRequestBuilder.setQuery(boolBuilder);
		}
		//设置排序
		for(String orderFiled:sortParams){
			FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(orderFiled).order(SortOrder.DESC);
			searchRequestBuilder.addSort(fieldSortBuilder);
		}
		try {
			SearchResponse response = searchRequestBuilder.execute().get();
			long totalHits = response.getHits().getTotalHits();
			SearchHit[] hits = response.getHits().getHits();
			for(SearchHit searchHitFields : hits){
				//获得查询数据
				Map<String, Object> source = searchHitFields.getSource();
				ExpertInfoFromEs expertInfoFromEs = new ExpertInfoFromEs();
				BeanUtils.populate(expertInfoFromEs, source);
				expertInfoFromEsList.add(expertInfoFromEs);
			}
			returnInfo.setTotal(totalHits);
			returnInfo.setRows(expertInfoFromEsList);
		} catch (InterruptedException e) {
			e.printStackTrace();
			code = "991";
			msg = e.getMessage();
		} catch (ExecutionException e) {
			e.printStackTrace();
			code = "992";
			msg = e.getMessage();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			code = "993";
			msg = e.getMessage();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			code = "994";
			msg = e.getMessage();
		}
		returnMsgResult.setCode(code);
		returnMsgResult.setMsg(msg);
		returnMsgResult.setResult(returnInfo);
		return returnMsgResult;
	}

	/**
	 * 根据es 脚本进行进行查询 对应app中默认排序
	 *
	 * @param index
	 * @param type
	 * @param begin
	 * @param size
	 * @param sortParams
	 * @param keyWord
	 * @return
	 * @throws ParseException
	 */
	public ReturnMsgResult getResultForDefaultSort(String index, String type, Map<String, String> filterMustParams,
												   Map<String, Object> sortParams,
												   String eClassCode,String rDirectCode,String orgID,int begin, int size) {
		List<ExpertInfoFromEs> expertInfoFromEsList = new ArrayList<ExpertInfoFromEs>();
		ReturnMsgResult returnMsgResult = new ReturnMsgResult();
		ReturnInfo returnInfo = new ReturnInfo();
		String code = "000";
		String msg = "";

		try{
			BoolQueryBuilder boolBuilder = null;
			if(StringUtils.isNotEmpty(eClassCode) || StringUtils.isNotEmpty(rDirectCode) ||
					StringUtils.isNotEmpty(orgID)){
				//建立bool查询
				boolBuilder = QueryBuilders.boolQuery();
				//使用should实现或者查询
				if(StringUtils.isNotEmpty(eClassCode)){
					//boolBuilder.must(QueryBuilders.matchPhraseQuery("typeCode",eClassCode));
					boolBuilder.must(QueryBuilders.wildcardQuery("typeCode",("*"+eClassCode+"*")));

				}
				if(StringUtils.isNotEmpty(rDirectCode)){
					//boolBuilder.must(QueryBuilders.matchPhraseQuery("rdcode",rDirectCode));
					boolBuilder.must(QueryBuilders.wildcardQuery("rdcode",("*"+rDirectCode+"*")));
				}
				if(StringUtils.isNotEmpty(orgID)){
					boolBuilder.must(QueryBuilders.matchQuery("orginationID",orgID));
				}
				boolBuilder.must(QueryBuilders.matchQuery("isuse","1"));
			}
			Script script = new Script(ScriptType.INLINE, "native", "expert_script", sortParams);
			ScriptScoreFunctionBuilder scriptBuilder = ScoreFunctionBuilders.scriptFunction(script);
			SearchRequestBuilder requestBuilder = esClinet.getEsclinet()
					.prepareSearch(index)
					.setTypes(type)
					.setQuery(new FunctionScoreQueryBuilder(scriptBuilder));
			if(boolBuilder != null){
				requestBuilder.setQuery(new FunctionScoreQueryBuilder(boolBuilder,scriptBuilder));
			}


			SearchResponse response = requestBuilder.setFrom(begin).setSize(size).execute().actionGet();
			long totalHits = response.getHits().getTotalHits();
			SearchHit[] hits = response.getHits().getHits();
			for(SearchHit searchHitFields : hits){
				//获得查询数据
				Map<String, Object> source = searchHitFields.getSource();
				ExpertInfoFromEs expertInfoFromEs = new ExpertInfoFromEs();
				BeanUtils.populate(expertInfoFromEs, source);
				expertInfoFromEsList.add(expertInfoFromEs);
			}
			returnInfo.setTotal(totalHits);
			returnInfo.setRows(expertInfoFromEsList);
		}catch (Exception e){
			e.printStackTrace();
			code = "999";
			msg = e.getMessage();
		}

		returnMsgResult.setCode(code);
		returnMsgResult.setMsg(msg);
		returnMsgResult.setResult(returnInfo);
		return returnMsgResult;
	}
	/**
	 * 根据es 脚本进行进行查询
	 *
	 * @param index
	 * @param type
	 * @param begin
	 * @param size
	 * @param sortParams
	 * @param keyWord
	 * @return
	 * @throws ParseException
	 */
	public ReturnMsgResult getResultForLocationSort(String index, String type, Map<String, String> filterParams, Map<String, Object> sortParams,
													String eClassCode,String rDirectCode,String orgID,int begin, int size) {
		List<ExpertInfoFromEs> expertInfoFromEsList = new ArrayList<ExpertInfoFromEs>();
		ReturnMsgResult returnMsgResult = new ReturnMsgResult();
		ReturnInfo returnInfo = new ReturnInfo();
		String code = "000";
		String msg = "";

		try{
			BoolQueryBuilder boolBuilder = null;
			if(StringUtils.isNotEmpty(eClassCode) || StringUtils.isNotEmpty(rDirectCode) ||
					StringUtils.isNotEmpty(orgID)){
				//建立bool查询
				boolBuilder = QueryBuilders.boolQuery();
				//使用should实现或者查询
				if(StringUtils.isNotEmpty(eClassCode)){
					//boolBuilder.must(QueryBuilders.matchPhraseQuery("typeCode",eClassCode));
					boolBuilder.must(QueryBuilders.wildcardQuery("typeCode",("*"+eClassCode+"*")));

				}
				if(StringUtils.isNotEmpty(rDirectCode)){
					//boolBuilder.must(QueryBuilders.matchPhraseQuery("rdcode",rDirectCode));
					boolBuilder.must(QueryBuilders.wildcardQuery("rdcode",("*"+rDirectCode+"*")));
				}
				if(StringUtils.isNotEmpty(orgID)){
					boolBuilder.must(QueryBuilders.matchQuery("orginationID",orgID));
				}
				boolBuilder.must(QueryBuilders.matchQuery("isuse","1"));
			}
			Script script = new Script(ScriptType.INLINE, "native", "expert_location_script", sortParams);
			ScriptScoreFunctionBuilder scriptBuilder = ScoreFunctionBuilders.scriptFunction(script);
			SearchRequestBuilder requestBuilder = esClinet.getEsclinet()
					.prepareSearch(index)
					.setTypes(type)
					.setQuery(new FunctionScoreQueryBuilder(scriptBuilder));
			if(boolBuilder != null){
				requestBuilder.setQuery(new FunctionScoreQueryBuilder(boolBuilder,scriptBuilder));
			}


			SearchResponse response = requestBuilder.setFrom(begin).setSize(size).execute().actionGet();
			long totalHits = response.getHits().getTotalHits();
			SearchHit[] hits = response.getHits().getHits();
			for(SearchHit searchHitFields : hits){
				//获得查询数据
				Map<String, Object> source = searchHitFields.getSource();
				ExpertInfoFromEs expertInfoFromEs = new ExpertInfoFromEs();
				BeanUtils.populate(expertInfoFromEs, source);
				expertInfoFromEsList.add(expertInfoFromEs);
			}
			returnInfo.setTotal(totalHits);
			returnInfo.setRows(expertInfoFromEsList);
		}catch (Exception e){
			e.printStackTrace();
			code = "999";
			msg = e.getMessage();
		}

		returnMsgResult.setCode(code);
		returnMsgResult.setMsg(msg);
		returnMsgResult.setResult(returnInfo);
		return returnMsgResult;
	}

	@Override
	public ReturnMsgResult getPest(String index, String type, Map<String, String> filterParams,String content,
								   Map<String, Object> sortParams, int begin, int size) {
		List<PestInfoFromEs> pestInfoFromEsList = new ArrayList<PestInfoFromEs>();
		ReturnMsgResult returnMsgResult = new ReturnMsgResult();
		ReturnInfo returnInfo = new ReturnInfo();
		String code = "000";
		String msg = "";
		try{
			BoolQueryBuilder boolBuilder = null;
			//使用should实现或者查询
			//建立bool查询
			boolBuilder = QueryBuilders.boolQuery();
			boolBuilder.should(QueryBuilders.matchQuery("name",content));
			boolBuilder.should(QueryBuilders.matchQuery("detail",content));
			boolBuilder.must(QueryBuilders.matchQuery("status","0"));
			SearchRequestBuilder requestBuilder = esClinet.getEsclinet()
					.prepareSearch(index)
					.setTypes(type)
					.setQuery(new FunctionScoreQueryBuilder(boolBuilder));

			SearchResponse response = requestBuilder.setFrom(begin).setSize(size).execute().actionGet();
			long totalHits = response.getHits().getTotalHits();
			SearchHit[] hits = response.getHits().getHits();
			for(SearchHit searchHitFields : hits){
				//获得查询数据
				Map<String, Object> source = searchHitFields.getSource();
				PestInfoFromEs pestInfoFromEs = new PestInfoFromEs();
				BeanUtils.populate(pestInfoFromEs, source);
				pestInfoFromEsList.add(pestInfoFromEs);
			}
			returnInfo.setTotal(totalHits);
			returnInfo.setRows(pestInfoFromEsList);
		}catch (Exception e){
			e.printStackTrace();
			code = "999";
			msg = e.getMessage();
		}
		returnMsgResult.setCode(code);
		returnMsgResult.setMsg(msg);
		returnMsgResult.setResult(returnInfo);
		return returnMsgResult;
	}
}
