package com.yling.es.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yling.es.service.EsSearchI;
import com.yling.es.utils.ConfigConstant;
import com.yling.es.utils.ConfigUtil;

@RestController
@RequestMapping("/classification")
public class RecommendCortoller {

	@Autowired
	private EsSearchI esSearchI;

	@Autowired
	public ConfigConstant configConstant;

	@RequestMapping(value = "/recommend", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getTechnologhyArticle(@RequestParam Map<String, Object> requestParams) {
		Map<String, String> filterMustParams = new HashMap<String, String>();
		String beginStr = (String) requestParams.get("begin");
		String sizeStr = (String) requestParams.get("size");
		String classificationSortString = (String) requestParams.get("classification");
		String concernCompanyString = (String) requestParams.get("concernCompany");
		String userProviCode = (String) requestParams.get("userProviCode");
		String userCityCode = (String) requestParams.get("userCityCode");
		String userCountyCode = (String) requestParams.get("userCountyCode");
		for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
			if (entry.getKey().contains("filter")) {
				String filterParamsStr = (String) entry.getValue();
				filterMustParams.put(filterParamsStr.split(",")[1], filterParamsStr.split(",")[0]);
			}

		}
		int begin = Integer.parseInt(beginStr);
		int size = Integer.parseInt(sizeStr);
		Map<String, Object> sortParams = new HashMap<String, Object>();
		sortParams.put("classification", classificationSortString);
		sortParams.put("concernCompany", concernCompanyString);
		sortParams.put("userProviCode", userProviCode);
		sortParams.put("userCityCode", userCityCode);
		sortParams.put("userCountyCode", userCountyCode);
		List<String> searchResultList = esSearchI.getResultByFunctionScore(configConstant.getEsIndex(),
				configConstant.getEsType(), filterMustParams, sortParams, begin, size);
		return searchResultList;
	}

	@RequestMapping(value = "/recommend/total", method = RequestMethod.POST)
	@ResponseBody
	public int getTechnologhyArticleTotalNum() {
		int totalNum = esSearchI.searchResultByMatchAll(configConstant.getEsIndex(), configConstant.getEsType());
		return totalNum;
	}

}
