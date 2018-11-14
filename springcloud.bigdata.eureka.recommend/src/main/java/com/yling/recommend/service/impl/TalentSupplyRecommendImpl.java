package com.yling.recommend.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yling.recommend.dao.TalentSupplyRecommendDao;
import com.yling.recommend.pojo.RecommendVO;
import com.yling.recommend.pojo.ReturnMsgResult;
import com.yling.recommend.service.TalentSupplyRecommendI;

@Service
public class TalentSupplyRecommendImpl implements TalentSupplyRecommendI {
	
	@Autowired
	private TalentSupplyRecommendDao talentSupplyRecommendDao;

	@Override
	public ReturnMsgResult getSupplyOrTalentRecommendByTypeAndId(String ID, String type) {
		ReturnMsgResult returnMsgResult = new ReturnMsgResult();
		Map map = new HashMap();
		String code = "000";
		List<RecommendVO> recommendList = talentSupplyRecommendDao.getTalentSupplyRecommendByIdAndType(ID, type);
		String msg = "The rest is success!";
		map.put("recommendResult", recommendList);
		returnMsgResult.setCode(code);
		returnMsgResult.setMsg(msg);
		returnMsgResult.setResult(map);
		return returnMsgResult;
	}

}
