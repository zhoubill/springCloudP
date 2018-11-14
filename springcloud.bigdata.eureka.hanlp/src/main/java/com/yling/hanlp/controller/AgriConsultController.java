package com.yling.hanlp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hankcs.hanlp.HanLP;
import com.yling.hanlp.pojo.ReturnMsgResult;
import com.yling.hanlp.service.AgriConsultHotWordI;

/**
 * @Author:zhouzhou
 * @Description:
 * @Date:2018/08/07 14:36
 * @Modified:
 */
@RestController
@RequestMapping("/agriconsult")
public class AgriConsultController {

	@Autowired
	private AgriConsultHotWordI agriConsultHotWordI;

	/**
	 * 农高会咨询热词分词
	 * 
	 * @param requestParams
	 * @return
	 */
	@RequestMapping(value = "/getConsultWord", method = RequestMethod.POST)
	@ResponseBody
	public ReturnMsgResult getAgriConsultWord(@RequestParam Map<String, Object> requestParams) {
		String id = (String) requestParams.get("id");
		return agriConsultHotWordI.getAgriConsultHotWord(id);
	}

	/**
	 * 根据内容获取关键词
	 * 
	 * @param requestParams
	 * @return
	 */
	@RequestMapping(value = "/getKeyWord", method = RequestMethod.POST)
	@ResponseBody
	public ReturnMsgResult getKeytWord(@RequestParam Map<String, Object> requestParams) {
		String content = (String) requestParams.get("content");
		List<String> keywordList = HanLP.extractKeyword(content, 10);
		int score = 10;
		Map<String, Integer> keywordMap = new HashMap<String, Integer>();
		for (String keyword : keywordList) {
			keywordMap.put(keyword, score);
			score--;
		}
		String code = "000";
		ReturnMsgResult returnMsgResult = new ReturnMsgResult();
		Map map = new HashMap();
		String msg = "已经处理成功了";
		map.put("keywordmap", keywordMap);
		returnMsgResult.setCode(code);
		returnMsgResult.setMsg(msg);
		returnMsgResult.setResult(map);
		return returnMsgResult;
	}

}
