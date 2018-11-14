package com.yling.hbase.utils;

import java.util.*;

public class HbaseResultUtils {


	/***
	 * 对获取到的资源统计分析的结果按照时间戳大的顺序去重结果
	 * @param summaryResults 结果
	 * @param dateIndex 日期下标
	 * @param typeIndex 类型下标
	 * @return
	 */
	public static List<String> removeDuplicateResult(List<String> summaryResults,int dateIndex,int typeIndex) {
		Map<String, String> temp = new HashMap<String, String>();
		Map<String, Double> tempMap = new HashMap<String, Double>();
		for (String summaryResult : summaryResults) {
			double summaryTimestamp = Double.parseDouble(summaryResult.split(",")[0].split("-")[1]);

			String summaryDate = summaryResult.split(",")[dateIndex];

			String summaryStatus = summaryResult.split(",")[typeIndex];
			if (!tempMap.containsKey(summaryDate + summaryStatus)) {
				tempMap.put(summaryDate + summaryStatus, summaryTimestamp);
				temp.put(summaryDate + summaryStatus, summaryResult);
			} else {
				//保留时间戳大的
				double summaryTimestampBefore = Double.valueOf(tempMap.get(summaryDate + summaryStatus));
				if (summaryTimestamp > summaryTimestampBefore) {
					tempMap.put(summaryDate + summaryStatus, summaryTimestamp);
					temp.put(summaryDate + summaryStatus, summaryResult);
				}
			}
		}
		return new ArrayList<String>(temp.values());
	}


}
