package com.yling.neo4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yling.neo4j.pojo.ReturnMsgResult;
import com.yling.neo4j.service.impl.AgriDiseasesGraphImpl;

@RestController
public class AgriDiseasesGraphController {
	
    @Autowired
	AgriDiseasesGraphImpl agriDiseasesGraphImpl;
	

	@RequestMapping(value = "/agriGraph", method = RequestMethod.POST)
	public ReturnMsgResult getAgriClassificationGraph(@RequestParam String deeplevel) {
		return agriDiseasesGraphImpl.getDataClassificationGraph(deeplevel);
	
	}
		
	@RequestMapping(value = "/agriAssociate", method = RequestMethod.POST)
	@ResponseBody
	public ReturnMsgResult getAgriDiseaseAssociate(String diseaseName) {
		return agriDiseasesGraphImpl.getAgriDiseasesAssociateFactor(diseaseName);
	}

}
