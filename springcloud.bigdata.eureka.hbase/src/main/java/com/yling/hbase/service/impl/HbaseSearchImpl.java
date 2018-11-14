package com.yling.hbase.service.impl;

import com.yling.hbase.constant.HbaseConstant;
import com.yling.hbase.pojo.ReturnInfo;
import com.yling.hbase.pojo.ReturnMsgResult;
import com.yling.hbase.pojo.UserActionRecord;
import com.yling.hbase.service.HbaseSearchI;
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
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class HbaseSearchImpl implements HbaseSearchI {
	
	@Autowired
	public HbaseClinet hbaseClinet; 
	@Autowired
	public HbasePageUtils hbasePageUtils;

    private static final String USER_ACTION_COLUMN_SIGN = "userBehavior";         //用户行为记录表标识

    /**
     * 从hbase中获取资源统计的结果
     *
     * @param startTime
     * @param endTime
     * @author zhouzhou
     */
    public List<String> getAgricultureProductSummary(String startTime, String endTime) {
        HTable table;
        List<String> AgricultureProductSummary = new ArrayList<String>();
        ResultScanner rs = null;
        try {
            table = (HTable) hbaseClinet.getHbaseClinet()
                    .getTable(TableName.valueOf("mall_agriculture_product_summary"));
            Scan scan = new Scan();
            scan.setCaching(HbaseConstant.SCAN_CACHING);// 设置扫描器缓存区的大小
            FilterList filterList = new FilterList();
            Filter stimeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("time"),
                    CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(startTime));
            filterList.addFilter(stimeFilter);
            Filter etimeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("time"),
                    CompareOp.LESS_OR_EQUAL, Bytes.toBytes(endTime));
            filterList.addFilter(etimeFilter);
            scan.setFilter(filterList);
            rs = table.getScanner(scan);
            for (Result r : rs) {
                StringBuffer resultString = new StringBuffer();
                resultString.append("key=").append(new String(r.getRow())).append(",");
                for (KeyValue key : r.raw()) {
                    resultString.append(new String(key.getFamily())).append(":").append(new String(key.getQualifier()))
                            .append("=").append(new String(key.getValue())).append(",");
                }

                AgricultureProductSummary.add(resultString.toString());

            }

            return HbaseResultUtils.removeDuplicateResult(AgricultureProductSummary, 2, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(rs != null){
                rs.close();
            }
        }

        return AgricultureProductSummary;

    }

    public List<String> getActivitySummary(String startTime, String endTime) {
        HTable table;
        List<String> activitySummary = new ArrayList<String>();
        ResultScanner rs = null;
        try {
            table = (HTable) hbaseClinet.getHbaseClinet()
                    .getTable(TableName.valueOf("bd_subject_activity_summary"));
            Scan scan = new Scan();
            scan.setCaching(HbaseConstant.SCAN_CACHING);// 设置扫描器缓存区的大小
            FilterList filterList = new FilterList();
            Filter stimeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("time"),
                    CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(startTime));
            filterList.addFilter(stimeFilter);
            Filter etimeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("time"),
                    CompareOp.LESS_OR_EQUAL, Bytes.toBytes(endTime));
            filterList.addFilter(etimeFilter);
            scan.setFilter(filterList);
            rs = table.getScanner(scan);
            for (Result r : rs) {
                StringBuffer resultString = new StringBuffer();
                resultString.append("key=").append(new String(r.getRow())).append(",");
                for (KeyValue key : r.raw()) {
                    resultString.append(new String(key.getFamily())).append(":").append(new String(key.getQualifier()))
                            .append("=").append(new String(key.getValue())).append(",");
                }

                activitySummary.add(resultString.toString());

            }

            return HbaseResultUtils.removeDuplicateResult(activitySummary, 2, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(rs != null){
                rs.close();
            }
        }

        return activitySummary;
    }

    /**
     * 从hbase中获取资讯统计的结果
     *
     * @param startTime
     * @param endTime
     * @author yzm
     */
    public List<String> getAgriServiceInfoSummary(String startTime, String endTime, String type) {
        HTable table;
        List<String> serviceInfoSummary = new ArrayList<String>();
        ResultScanner rs = null;
        try {
            table = (HTable) hbaseClinet.getHbaseClinet()
                    .getTable(TableName.valueOf("bd_agri_service_info_summary"));
            Scan scan = new Scan();
            scan.setCaching(HbaseConstant.SCAN_CACHING);// 设置扫描器缓存区的大小
            FilterList filterList = new FilterList();
            if (StringUtils.isNotEmpty(startTime)) {
                Filter stimeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("time"),
                        CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(startTime));
                filterList.addFilter(stimeFilter);
            }
            if (StringUtils.isNotEmpty(endTime)) {
                Filter etimeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("time"),
                        CompareOp.LESS_OR_EQUAL, Bytes.toBytes(endTime));
                filterList.addFilter(etimeFilter);
            }
            if (StringUtils.isNotEmpty(type)) {
                Filter typeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("type"),
                        CompareOp.EQUAL, Bytes.toBytes(type));
                filterList.addFilter(typeFilter);
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

                serviceInfoSummary.add(resultString.toString());

            }
            return HbaseResultUtils.removeDuplicateResult(serviceInfoSummary, 1, 3);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(rs != null){
                rs.close();
            }
        }

        return serviceInfoSummary;
    }

    public List<String> getMallOrderSummary(String startTime, String endTime) {
        HTable table;
        List<String> mallOrderSummary = new ArrayList<String>();
        ResultScanner rs = null;
        try {
            table = (HTable) hbaseClinet.getHbaseClinet()
                    .getTable(TableName.valueOf("mall_order_info_summary"));
            Scan scan = new Scan();
            scan.setCaching(HbaseConstant.SCAN_CACHING);// 设置扫描器缓存区的大小
            FilterList filterList = new FilterList();
            Filter stimeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("time"),
                    CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(startTime));
            filterList.addFilter(stimeFilter);
            Filter etimeFilter = new SingleColumnValueFilter(Bytes.toBytes("record"), Bytes.toBytes("time"),
                    CompareOp.LESS_OR_EQUAL, Bytes.toBytes(endTime));
            filterList.addFilter(etimeFilter);
            scan.setFilter(filterList);
            rs = table.getScanner(scan);
            for (Result r : rs) {
                StringBuffer resultString = new StringBuffer();
                resultString.append("key=").append(new String(r.getRow())).append(",");
                for (KeyValue key : r.raw()) {
                    resultString.append(new String(key.getFamily())).append(":").append(new String(key.getQualifier()))
                            .append("=").append(new String(key.getValue())).append(",");
                }

                mallOrderSummary.add(resultString.toString());

            }

            return HbaseResultUtils.removeDuplicateResult(mallOrderSummary, 2, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(rs != null){
                rs.close();
            }
        }

        return mallOrderSummary;
    }

    /**
     * 分页查询用户行为记录(浏览、收藏)
     *
     * @param topType     browse浏览 store收藏
     * @param contentType product共享 serviceInfo 资讯 activity 活动
     * @param contentId
     * @param accountId
     * @param userType    10-普通 14-专家 0-游客
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     */
    public ReturnMsgResult getUserActionRecordPage(String topType, String contentType, String contentId, String accountId, String userType, String startTime, String endTime, Integer page, Integer limit) {
        List<UserActionRecord> userActionRecordList = new LinkedList<UserActionRecord>();
        ReturnMsgResult returnMsgResult = new ReturnMsgResult();
        ReturnInfo returnInfo = new ReturnInfo();
        String code = "000";
        String msg = "";
        ResultScanner rs = null;
        try {
            FilterList filterList = setFilterListForUserAction(topType, contentType, contentId, accountId,       //设置过滤条件
                    userType, startTime, endTime);
            Map<String, Object> resultMap = hbasePageUtils.getResultScannerByPage("user_action", filterList, page, limit);  //获取分页结果
            rs = (ResultScanner) resultMap.get("resultScanner");  //获取指定页的数据
            returnInfo.setTotal((Long) resultMap.get("count"));
            if (rs != null) {
                for (Result r : rs) {   //每条记录
                    UserActionRecord userActionRecord = new UserActionRecord();
                    for (KeyValue key : r.raw()) {
                        String name = new String(key.getQualifier());
                        String value = new String(key.getValue());
                        if (name.equals("contentId")) {
                            userActionRecord.contentId = value;
                        } else if (name.equals("requestTime")) {
                            userActionRecord.requestTime = value;
                        } else if (name.equals("userType")) {
                            userActionRecord.userType = value;
                        } else if (name.equals("accountId")) {
                            userActionRecord.userAccId = value;
                        } else if (name.equals("topType")) {
                            userActionRecord.topType = value;
                        } else if (name.equals("contentType")) {
                            userActionRecord.contentType = value;
                        }
                    }

                    userActionRecordList.add(userActionRecord);
                }
            }
            //对游客的userAccId和userType进行处理
            for (UserActionRecord userActionRecord : userActionRecordList) {
                if ("visitors-user".equals(userActionRecord.userAccId)) {
                    userActionRecord.userAccId = "";
                    userActionRecord.userType = "0";
                }
            }
            returnInfo.setRows(userActionRecordList);
        } catch (Throwable e) {
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

    /**
     * 所有查询用户行为记录(浏览、收藏)
     *
     * @param topType     browse浏览 store收藏
     * @param contentType product共享 serviceInfo 资讯 activity 活动
     * @param contentId
     * @param accountId
     * @param userType    10-普通 14-专家 0-游客
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     */
    public ReturnMsgResult getUserActionRecordAll(String topType, String contentType, String contentId, String accountId, String userType, String startTime, String endTime) {
        List<UserActionRecord> userActionRecordList = new LinkedList<UserActionRecord>();
        ReturnMsgResult returnMsgResult = new ReturnMsgResult();
        ReturnInfo returnInfo = new ReturnInfo();
        String code = "000";
        String msg = "";
        HTable table;
        ResultScanner rs = null;
        try {
            table = (HTable) hbaseClinet.getHbaseClinet()
                    .getTable(TableName.valueOf("user_action"));
            Scan scan = new Scan();
            scan.setCaching(HbaseConstant.SCAN_CACHING);// 设置扫描器缓存区的大小
            FilterList filterList = setFilterListForUserAction(topType, contentType, contentId, accountId,       //设置过滤条件
                    userType, startTime, endTime);
            scan.setFilter(filterList);
            //统计总条数
            AggregationClient ac = new AggregationClient(hbaseClinet.getHbaseConfiguration());
            Long count = ac.rowCount(TableName.valueOf("user_action"), new LongColumnInterpreter(), scan);
            returnInfo.setTotal(count);
            rs = table.getScanner(scan);
            if (rs != null) {
                for (Result r : rs) {   //每条记录
                    UserActionRecord userActionRecord = new UserActionRecord();
                    for (KeyValue key : r.raw()) {
                        String name = new String(key.getQualifier());
                        String value = new String(key.getValue());
                        if (name.equals("contentId")) {
                            userActionRecord.contentId = value;
                        } else if (name.equals("requestTime")) {
                            userActionRecord.requestTime = value;
                        } else if (name.equals("userType")) {
                            userActionRecord.userType = value;
                        } else if (name.equals("accountId")) {
                            userActionRecord.userAccId = value;
                        } else if (name.equals("topType")) {
                            userActionRecord.topType = value;
                        } else if (name.equals("contentType")) {
                            userActionRecord.contentType = value;
                        }
                    }

                    userActionRecordList.add(userActionRecord);
                }
            }
            //对游客的userAccId和userType进行处理
            for (UserActionRecord userActionRecord : userActionRecordList) {
                if ("visitors-user".equals(userActionRecord.userAccId)) {
                    userActionRecord.userAccId = "";
                    userActionRecord.userType = "0";
                }
            }
            returnInfo.setRows(userActionRecordList);
        } catch (Throwable e) {
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

    /**
     * 设置过滤----用户行为
     *
     * @return
     */
    public FilterList setFilterListForUserAction(String topType, String contentType, String contentId, String accountId,
                                                 String userType, String startTime, String endTime) {
        FilterList filterList = new FilterList();
        Filter filter = null;
        if (StringUtils.isNotEmpty(topType)) {          // 浏览、收藏
            filter = new SingleColumnValueFilter(Bytes.toBytes(USER_ACTION_COLUMN_SIGN), Bytes.toBytes("topType"),
                    CompareOp.EQUAL, Bytes.toBytes(topType));
            filterList.addFilter(filter);
        }
        if (StringUtils.isNotEmpty(contentType)) {
            filter = new SingleColumnValueFilter(Bytes.toBytes(USER_ACTION_COLUMN_SIGN), Bytes.toBytes("contentType"),
                    CompareOp.EQUAL, Bytes.toBytes(contentType));
            filterList.addFilter(filter);
        }
        if (StringUtils.isNotEmpty(contentId)) {
            filter = new SingleColumnValueFilter(Bytes.toBytes(USER_ACTION_COLUMN_SIGN), Bytes.toBytes("contentId"),
                    CompareOp.EQUAL, Bytes.toBytes(contentId));
            filterList.addFilter(filter);
        }
        if (StringUtils.isNotEmpty(accountId)) {
            filter = new SingleColumnValueFilter(Bytes.toBytes(USER_ACTION_COLUMN_SIGN), Bytes.toBytes("accountId"),
                    CompareOp.EQUAL, Bytes.toBytes(accountId));
            filterList.addFilter(filter);
        }
        if (StringUtils.isNotEmpty(userType)) {
            filter = new SingleColumnValueFilter(Bytes.toBytes(USER_ACTION_COLUMN_SIGN), Bytes.toBytes("userType"),
                    CompareOp.EQUAL, Bytes.toBytes(userType));
            filterList.addFilter(filter);
        }
        if (StringUtils.isNotEmpty(startTime)) {
            filter = new SingleColumnValueFilter(Bytes.toBytes(USER_ACTION_COLUMN_SIGN), Bytes.toBytes("requestTime"),
                    CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(startTime));
            filterList.addFilter(filter);
        }
        if (StringUtils.isNotEmpty(endTime)) {
            filter = new SingleColumnValueFilter(Bytes.toBytes(USER_ACTION_COLUMN_SIGN), Bytes.toBytes("requestTime"),
                    CompareOp.LESS_OR_EQUAL, Bytes.toBytes(endTime));
            filterList.addFilter(filter);
        }
        return filterList;
    }
}
