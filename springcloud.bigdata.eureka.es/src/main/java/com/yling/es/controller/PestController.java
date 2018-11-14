package com.yling.es.controller;


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
import com.yling.es.utils.ConfigUtil;

/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/10/28 17:13
 * @Modified:
 */
@RestController
@RequestMapping("/pest")
public class PestController {
    @Autowired
    private EsSearchI esSearchI;
    
    @Autowired
	public ConfigConstant configConstant;
    //病虫害
    @RequestMapping(value = "/getPest", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgResult getPest(@RequestBody Map<String, Object> requestParams){
        int begin =  (Integer)requestParams.get("begin");
        int size = (Integer) requestParams.get("size");
        String content = (String)requestParams.get("content");
        ReturnMsgResult returnMsgResult = esSearchI.getPest(
        		configConstant.getEsPestIndex(),
        		configConstant.getEsPestType(),
                null,content,null,begin,size
        );
        return returnMsgResult;
    }
}
