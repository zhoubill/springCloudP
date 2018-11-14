package com.yling.neo4j.pojo;



/**
 * Created by Administrator on 2016/11/28.
 */
public class ReturnMsgResult {
    private String code;//返回编码
    private String msg;//返回信息
    private Object result;//返回对象

    public ReturnMsgResult() {
    }

    public ReturnMsgResult(String code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ReturnMsgResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
