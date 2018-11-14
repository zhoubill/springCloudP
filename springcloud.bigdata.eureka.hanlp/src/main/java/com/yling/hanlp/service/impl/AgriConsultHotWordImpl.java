package com.yling.hanlp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.yling.hanlp.dao.AgriConsultDao;
import com.yling.hanlp.pojo.AgriConsult;
import com.yling.hanlp.pojo.AgriConsultHotWord;
import com.yling.hanlp.pojo.ReturnMsgResult;
import com.yling.hanlp.service.AgriConsultHotWordI;


/**
 * @Author:zhouzhou
 * @Description:
 * @Date:2018/8/19 9:56
 * @Modified:
 */
@Service
public class AgriConsultHotWordImpl implements AgriConsultHotWordI {

	@Autowired
	private AgriConsultDao agriConsultDao;

	/**
	 * 根据咨询问题ID获取咨询问题的文字描述，进行分词的服务
	 * 
	 */
	@Override
	public ReturnMsgResult getAgriConsultHotWord(String id) {
		String code = "000";
		ReturnMsgResult returnMsgResult = new ReturnMsgResult();
		List<AgriConsult> agriConsultList = agriConsultDao.getAllAgriConsultById(id);
		List<String> wordPropertiesList = new ArrayList<String>();
		wordPropertiesList.add("w");
		wordPropertiesList.add("wb");
		wordPropertiesList.add("wd");
		wordPropertiesList.add("wf");
		wordPropertiesList.add("wh");
		wordPropertiesList.add("ule");
		wordPropertiesList.add("ude1");
		wordPropertiesList.add("ude3");
		wordPropertiesList.add("m");
		wordPropertiesList.add("y");
		wordPropertiesList.add("z");
		wordPropertiesList.add("u");
		wordPropertiesList.add("ud");
		wordPropertiesList.add("o");
		wordPropertiesList.add("p");
		wordPropertiesList.add("q");
		wordPropertiesList.add("qg");
		wordPropertiesList.add("qv");
		if (!CollectionUtils.isEmpty(agriConsultList)) {
			AgriConsult agriconsult = agriConsultList.get(0);
			//删除结果表日期中含有的分词
			agriConsultDao.deleteAgriConsultWord(agriconsult.getDate());
			List<String> agriconsultwordList = new ArrayList<String>();
			List<Term> wordList = HanLP.segment(agriconsult.getQuestion_all());
			for (Term word : wordList) {
				if(!wordPropertiesList.contains(word.nature.name())) {
					agriconsultwordList.add(word.word);
				}
			}
			Map map = new HashMap();
			//利用java8的特性对分词进行分组计数
			Map<String,Long> wordResult = agriconsultwordList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			List<AgriConsultHotWord> agriConsultWordList =  new ArrayList<AgriConsultHotWord>();
			if(!CollectionUtils.isEmpty(wordResult)) {
				for (Map.Entry<String, Long> entry : wordResult.entrySet()) {
					AgriConsultHotWord agriConsultWord =  new AgriConsultHotWord();
					agriConsultWord.setCount(entry.getValue().intValue());
					agriConsultWord.setHot_word(entry.getKey());
					agriConsultWord.setDate(agriconsult.getDate());
					agriConsultWord.setCreateAt(new Date());
					agriConsultWord.setAgriConsultId(id);
					agriConsultWord.setSort(0);
					agriConsultWord.setStatus(0);
					agriConsultWord.setVersion(0);
					agriConsultWord.setUpdateAt(new Date());
					agriConsultWordList.add(agriConsultWord);
				}
				//批量插入农高会咨询问题分词的统计结果
				agriConsultDao.bathInsertAgriConsultHotWord(agriConsultWordList);
			}
			
			//更改已经处理的状态
			agriConsultDao.updateAgriConsult(id, 1);
			String msg = "已经处理成功了";
			map.put("agriconsult", wordResult);
			returnMsgResult.setCode(code);
			returnMsgResult.setMsg(msg);
			returnMsgResult.setResult(map);
			return returnMsgResult;
		}
		return returnMsgResult;
	}

}
