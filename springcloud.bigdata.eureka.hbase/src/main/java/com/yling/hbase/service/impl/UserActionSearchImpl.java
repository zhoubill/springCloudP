package com.yling.hbase.service.impl;


import com.yling.hbase.constant.HbaseConstant;
import com.yling.hbase.pojo.ReturnInfo;
import com.yling.hbase.pojo.ReturnMsgResult;
import com.yling.hbase.pojo.UserActionSummary;
import com.yling.hbase.service.UserActionSearchI;
import com.yling.hbase.utils.HbaseClinet;
import com.yling.hbase.utils.HbasePageUtils;
import com.yling.hbase.utils.HbaseResultUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/8/15 17:16
 * @Modified:
 */
@Service
public class UserActionSearchImpl implements UserActionSearchI {
	
	@Autowired
	public HbaseClinet hbaseClinet; 
	@Autowired
	public HbasePageUtils hbasePageUtils;
	
    public List<String> getUserActionOperSummary(String startTime, String endTime,String queryType,String contentType) {
        HTable table;
        List<String> userActionOperSummary = new ArrayList<String>();
        ResultScanner rs = null;
        try{
            table = (HTable) hbaseClinet.getHbaseClinet()
                    .getTable(TableName.valueOf("user_action_oper_summary"));
            Scan scan = new Scan();
            scan.setCaching(HbaseConstant.SCAN_CACHING);// 设置扫描器缓存区的大小
            FilterList filterList = new FilterList();
            if(StringUtils.isNotEmpty(startTime)){
                Filter stimeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("requestdate"),
                        CompareFilter.CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(startTime));
                filterList.addFilter(stimeFilter);
            }
            if(StringUtils.isNotEmpty(endTime)){
                Filter etimeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("requestdate"),
                        CompareFilter.CompareOp.LESS_OR_EQUAL, Bytes.toBytes(endTime));
                filterList.addFilter(etimeFilter);
            }
            if(StringUtils.isNotEmpty(queryType)){
                Filter topTypeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("topType"),
                        CompareFilter.CompareOp.EQUAL, Bytes.toBytes(queryType));
                filterList.addFilter(topTypeFilter);
            }
            if(StringUtils.isNotEmpty(contentType)){
                Filter contentTypeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("contenttype"),
                        CompareFilter.CompareOp.EQUAL, Bytes.toBytes(contentType));
                filterList.addFilter(contentTypeFilter);
            }
            scan.setFilter(filterList);
            rs = table.getScanner(scan);
            for (Result r : rs) {
                StringBuffer resultString = new StringBuffer();
                resultString.append("key=").append(new String(r.getRow())).append(",");
                for (KeyValue key : r.raw()) {
                    resultString.append(new String(key.getFamily())).append(":").append(new String(key.getQualifier()))
                            .append("=").append(new String(key.getValue())).append(",");
                }

                userActionOperSummary.add(resultString.toString());

            }
            return HbaseResultUtils.removeDuplicateResult(userActionOperSummary,1,3);
//            return userActionOperSummary;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null){
                rs.close();
            }
        }
        return userActionOperSummary;
    }

    public ReturnMsgResult getUserActionCount(String queryType, Integer page, Integer limit) {
        List<UserActionSummary> userActionSummaries = new ArrayList<UserActionSummary>();
        ReturnMsgResult returnMsgResult = new ReturnMsgResult();
        ReturnInfo returnInfo = new ReturnInfo();
        String code = "000";
        String msg = "";
        ResultScanner rs = null;
        try{
            FilterList filterList = new FilterList();
            if(StringUtils.isNotEmpty(queryType)){
                Filter topTypeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("contenttype"),
                        CompareFilter.CompareOp.EQUAL, Bytes.toBytes(queryType));
                filterList.addFilter(topTypeFilter);
            }
            Map<String, Object> resultMap = hbasePageUtils.getResultScannerByPage("user_action_summary",filterList,page,limit);
            rs = (ResultScanner) resultMap.get("resultScanner");  //获取指定页的数据
            returnInfo.setTotal((Long) resultMap.get("count"));
            if(rs != null){
                for (Result r : rs) {   //每条记录
                    UserActionSummary userActionSummary = new UserActionSummary();
                    for (KeyValue key : r.raw()) {
                        String name = new String(key.getQualifier());
                        String value = new String(key.getValue());
                        if (name.equals("contentId")) {
                            userActionSummary.contentId = value;
                        } else if (name.equals("browsenum")) {
                            userActionSummary.browseNum = value;
                        }else if (name.equals("storenum")) {
                            userActionSummary.storeNum = value;
                        }
                    }
                    userActionSummaries.add(userActionSummary);
                }
            }
            returnInfo.setRows(userActionSummaries);
        }catch (Throwable e){
            e.printStackTrace();
            code = "991";
            msg = "failed";
        }finally {
            if(rs != null){
                rs.close();
            }
        }
        returnMsgResult.setCode(code);
        returnMsgResult.setMsg(msg);
        returnMsgResult.setResult(returnInfo);
        return returnMsgResult;
    }
}
