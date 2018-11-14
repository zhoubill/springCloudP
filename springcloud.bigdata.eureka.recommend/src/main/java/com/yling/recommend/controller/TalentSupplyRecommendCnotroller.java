package com.yling.recommend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yling.recommend.pojo.ReturnMsgResult;
import com.yling.recommend.service.TalentSupplyRecommendI;

@RestController
@RequestMapping("/talentSupplyRec")
public class TalentSupplyRecommendCnotroller {
	
	
	@Autowired
	private TalentSupplyRecommendI talentSupplyRecommendI;
	
	
	@RequestMapping(value = "/recommend", method = RequestMethod.POST)
	@ResponseBody 
	public ReturnMsgResult getRecommendTalentOrSupply(String ID, String type) {		
		return talentSupplyRecommendI.getSupplyOrTalentRecommendByTypeAndId(ID, type);
		
	} 

}
