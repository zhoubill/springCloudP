package com.yling.hbase.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yling.hbase.constant.HbaseConstant;
import com.yling.hbase.pojo.ReturnMsgResult;
import com.yling.hbase.pojo.UserImageAge;
import com.yling.hbase.pojo.UserImageArea;
import com.yling.hbase.pojo.UserImageSex;
import com.yling.hbase.service.UserImageSearchI;
import com.yling.hbase.utils.HbaseClinet;
import com.yling.hbase.utils.HbasePageUtils;

/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/8/16 22:40
 * @Modified:
 */
@Service
public class UserImageSearchImpl implements UserImageSearchI {
	@Autowired
	public HbaseClinet hbaseClinet; 
	
    public ReturnMsgResult getUserImageAge() {
        List<UserImageAge> userImageAgeList = new ArrayList();
        String code = "000";
        String msg = "";
        ResultScanner rs = null;
        try{
            HTable table= (HTable) hbaseClinet.getHbaseClinet()
                    .getTable(TableName.valueOf("user_image_age"));
            Scan scan = new Scan();
            scan.setCaching(HbaseConstant.SCAN_CACHING);// 设置扫描器缓存区的大小
            rs = table.getScanner(scan);
            if(rs != null){
                for (Result r : rs) {   //每条记录
                    UserImageAge userImageAge = new UserImageAge();
                    for (KeyValue key : r.raw()) {
                        String name = new String(key.getQualifier());
                        String value = new String(key.getValue());
                        if (name.equals("age")) {
                            if(value != null){
                                userImageAge.age = Integer.parseInt(value);
                            }

                        } else if (name.equals("totalnum")) {
                            if(value != null){
                                userImageAge.total = Integer.parseInt(value);
                            }
                        }
                    }
                    userImageAgeList.add(userImageAge);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null){
                rs.close();
            }
        }
        ReturnMsgResult returnMsgResult = new ReturnMsgResult();
        returnMsgResult.setCode(code);
        returnMsgResult.setMsg(msg);
        returnMsgResult.setResult(userImageAgeList);
        return returnMsgResult;
    }

    public ReturnMsgResult getUserImageSex() {
        List<UserImageSex> userImageSexList = new ArrayList();
        String code = "000";
        String msg = "";
        ResultScanner rs = null;
        try{
            HTable table= (HTable) hbaseClinet.getHbaseClinet()
                    .getTable(TableName.valueOf("user_image_sex"));
            Scan scan = new Scan();
            scan.setCaching(HbaseConstant.SCAN_CACHING);// 设置扫描器缓存区的大小
            rs = table.getScanner(scan);
            if(rs != null){
                for (Result r : rs) {   //每条记录
                    UserImageSex userImageSex = new UserImageSex();
                    for (KeyValue key : r.raw()) {
                        String name = new String(key.getQualifier());
                        String value = new String(key.getValue());
                        if (name.equals("sex")) {
                            userImageSex.sex = value;

                        } else if (name.equals("totalnum")) {
                            if(value != null){
                                userImageSex.total = Integer.parseInt(value);
                            }
                        }
                    }
                    userImageSexList.add(userImageSex);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null){
                rs.close();
            }
        }
        ReturnMsgResult returnMsgResult = new ReturnMsgResult();
        returnMsgResult.setCode(code);
        returnMsgResult.setMsg(msg);
        returnMsgResult.setResult(userImageSexList);
        return returnMsgResult;
    }

    public ReturnMsgResult getUserImageArea() {
        List<UserImageArea> userImageAreaList = new ArrayList();
        String code = "000";
        String msg = "";
        ResultScanner rs = null;
        try{
            HTable table= (HTable) hbaseClinet.getHbaseClinet()
                    .getTable(TableName.valueOf("user_image_area"));
            Scan scan = new Scan();
            scan.setCaching(HbaseConstant.SCAN_CACHING);// 设置扫描器缓存区的大小
            rs = table.getScanner(scan);
            if(rs != null){
                for (Result r : rs) {   //每条记录
                    UserImageArea userImageArea = new UserImageArea();
                    for (KeyValue key : r.raw()) {
                        String name = new String(key.getQualifier());
                        String value = new String(key.getValue(),"UTF-8");
                        if (name.equals("provincename")) {
                            userImageArea.provinceName = value;

                        } else if (name.equals("totalnum")) {
                            if(value != null){
                                userImageArea.total = Integer.parseInt(value);
                            }
                        }
                    }
                    userImageAreaList.add(userImageArea);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null){
                rs.close();
            }
        }
        ReturnMsgResult returnMsgResult = new ReturnMsgResult();
        returnMsgResult.setCode(code);
        returnMsgResult.setMsg(msg);
        returnMsgResult.setResult(userImageAreaList);
        return returnMsgResult;
    }

    public ReturnMsgResult getUserImage() {
        String code = "000";
        String msg = "";
        ReturnMsgResult retAge = getUserImageAge();
        ReturnMsgResult retSex = getUserImageSex();
        ReturnMsgResult retArea = getUserImageArea();

        Map map = new HashMap();
        map.put("UserImageAge",retAge.getResult());
        map.put("UserImageSex",retSex.getResult());
        map.put("UserImageArea",retArea.getResult());

        ReturnMsgResult returnMsgResult = new ReturnMsgResult();
        returnMsgResult.setCode(code);
        returnMsgResult.setMsg(msg);
        returnMsgResult.setResult(map);
        return returnMsgResult;
    }
}
