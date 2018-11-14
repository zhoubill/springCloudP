package com.yling.hbase.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author:yzm
 * @Description: 分页处理
 * @Date:2018/08/14 14:36
 * @Modified:
 */
@Component
public class HbasePageUtils {
	
	@Autowired
	public HbaseClinet hbaseClinet;

    /**
     * 获取分页数据
     * @param tableName 表名
     * @param filterList 过滤
     * @param page 第几页
     * @param limit 每页条数
     * @return
     * @throws Throwable
     */
    public Map<String,Object> getResultScannerByPage(String tableName,FilterList filterList,Integer page, Integer limit) throws Throwable{
        String startRowKey = getStartRowKey(tableName, filterList, page, limit);                           //获取指定页的StartRow
        Map<String,Object> map = getDate(tableName, filterList, startRowKey, limit, true);  //获取指定页的数据
        return map;
    }

    /**
     * 获取指定页的startRowKey
     *
     * @param tableName 表名
     * @param page      第几页
     * @param limit     每页条数
     * @throws Exception
     */
    public String getStartRowKey(String tableName, FilterList filterList, Integer page, Integer limit) throws Throwable {

        //如果page不标准，比如是0 或者-1那么就按照第一页处理
        if (page <= 1) {
            return null;
        } else {
            //解决思路 我们每页获取pagesize+1条记录
            //每次遍历记录下最后一条记录的rowkey，就是下一页的开始rowkey
            String startRowKey = null;

            for (int i = 1; i < page; i++) {
                Map<String,Object> map = getDate(tableName, filterList, startRowKey, limit + 1, false);
                ResultScanner rs = (ResultScanner)map.get("resultScanner");
                int pageNum = 0;             //当页条数
                if(rs != null){
                    Iterator<Result> iterator = rs.iterator();
                    Result next = null;

                    while (iterator.hasNext()) {
                        pageNum ++;
                        next = iterator.next();
                    }
                    startRowKey = Bytes.toString(next.getRow());

                    rs.close();
                }
                if(pageNum < limit + 1){     //如果下一页已经没数据
                    startRowKey = "no";
                    break;
                }
            }

            return startRowKey;
        }
    }

    /**
     * 获取数据
     *
     * @param tableName   表名
     * @param startRowKey 指定页的startRowKey
     * @param limit       每页条数
     * @param isNeedCount 是否需要count
     * @return Map<String,Object>  返回记录和总记录数
     * @throws Exception
     */
    public Map<String,Object> getDate(String tableName, FilterList filterList, String startRowKey, Integer limit, Boolean isNeedCount) throws Throwable {
        Map<String,Object> map = new HashMap<String, Object>();
        HTable table = (HTable) hbaseClinet.getHbaseClinet()
                .getTable(TableName.valueOf(tableName));
        FilterList filterListTemp = new FilterList(filterList);
        Scan scan = new Scan();
        scan.setCaching(1000);// 设置扫描器缓存区的大小


        if (isNeedCount) {
            scan.setFilter(filterListTemp);
            //统计总条数
            AggregationClient ac = new AggregationClient(hbaseClinet.getHbaseConfiguration());
            Long count = ac.rowCount(TableName.valueOf(tableName), new LongColumnInterpreter(), scan);

            map.put("count",count);
        }
        //分页
        if (StringUtils.isNotEmpty(startRowKey)) {
            scan.setStartRow(startRowKey.getBytes());
        }
        Filter pageFilter = new PageFilter(limit);
        filterListTemp.addFilter(pageFilter);

        scan.setFilter(filterListTemp);

        ResultScanner rs = "no".equals(startRowKey) ? null: table.getScanner(scan);
        map.put("resultScanner",rs);
        return map;
    }
}
