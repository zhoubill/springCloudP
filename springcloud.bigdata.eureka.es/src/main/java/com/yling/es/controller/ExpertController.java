package com.yling.es.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yling.es.pojo.ReturnMsgResult;
import com.yling.es.service.EsSearchI;
import com.yling.es.utils.ConfigConstant;

/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/5/10 9:56
 * @Modified:
 */
@RestController
@RequestMapping("/expert")
public class ExpertController {
    @Autowired
    private EsSearchI esSearchI;
    
    @Autowired
	public ConfigConstant configConstant;
    
    //	推荐排序
    @RequestMapping(value = "/recommend", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgResult getExpertInfoForRecommend(@RequestBody Map<String, Object> requestParams){
        int begin =  (Integer)requestParams.get("begin");
        int size = (Integer) requestParams.get("size");
        String eClassCode = (String)requestParams.get("eClassCode");
        String rDirectCode = (String) requestParams.get("rDirectCode");
        String orgID = (String)requestParams.get("orgID");
        List<String> sortParams = new ArrayList<String>();
        sortParams.add("answerNum");
        sortParams.add("questionNUm");
        sortParams.add("studentNum");
        ReturnMsgResult returnMsgResult = esSearchI.getResultForSort(
        		configConstant.getEsExpertIndex(),
        		configConstant.getEsExpertType(),
                sortParams,eClassCode,rDirectCode,orgID,begin,size
        );
        return returnMsgResult;
    }
    //默认排序
    @RequestMapping(value = "/defaultSort", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgResult getExpertInfoForDefaultSort(@RequestBody Map<String, Object> requestParams){
        int begin =  (Integer)requestParams.get("begin");
        int size = (Integer) requestParams.get("size");
        String userProviCode = (String) requestParams.get("userProviCode");
        String userCityCode = (String) requestParams.get("userCityCode");
        String userCountyCode = (String) requestParams.get("userCountyCode");
        String concernCompany = (String) requestParams.get("concernCompany");
        String userLat = (String) requestParams.get("userLat");
        String userLng = (String) requestParams.get("userLng");
        String eClassCode = (String)requestParams.get("eClassCode");
        String rDirectCode = (String) requestParams.get("rDirectCode");
        String orgID = (String)requestParams.get("orgID");
        Map<String, Object> sortParams = new HashMap<String, Object>();
        sortParams.put("userProviCode", userProviCode);
        sortParams.put("userCityCode", userCityCode);
        sortParams.put("userCountyCode", userCountyCode);
        sortParams.put("concernCompany", concernCompany);
        sortParams.put("userLat", userLat);
        sortParams.put("userLng", userLng);
        ReturnMsgResult returnMsgResult = esSearchI.getResultForDefaultSort(
        		configConstant.getEsExpertIndex(),
        		configConstant.getEsExpertType(),
                null, sortParams,eClassCode,rDirectCode,orgID,begin, size
        );
        return returnMsgResult;
    }

    //距离排序
    @RequestMapping(value = "/locationSort", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgResult getExpertInfoForLocationSort(@RequestBody Map<String, Object> requestParams){
        int begin =  (Integer)requestParams.get("begin");
        int size = (Integer) requestParams.get("size");
        String userLat = (String) requestParams.get("userLat");
        String userLng = (String) requestParams.get("userLng");
        String eClassCode = (String)requestParams.get("eClassCode");
        String rDirectCode = (String) requestParams.get("rDirectCode");
        String orgID = (String)requestParams.get("orgID");
        Map<String, Object> sortParams = new HashMap<String, Object>();
        sortParams.put("userLat", userLat);
        sortParams.put("userLng", userLng);
        ReturnMsgResult returnMsgResult = esSearchI.getResultForLocationSort(
        		configConstant.getEsExpertIndex(),
        		configConstant.getEsExpertType(),
                null, sortParams, eClassCode,rDirectCode,orgID,begin, size
        );
        return returnMsgResult;
    }
}
