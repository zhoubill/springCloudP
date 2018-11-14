package com.yling.hanlp.pojo;



import java.util.Date;

/**
 * @Author:zhouzhou
 * @Description:
 * @Date:2018/5/10 11:21
 * @Modified:
 */
public class AgriConsult {

	public Date date;

	public String Id;

	public String question_all;

	public Integer deal_status;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getDeal_status() {
		return deal_status;
	}

	public void setDeal_status(Integer deal_status) {
		this.deal_status = deal_status;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getQuestion_all() {
		return question_all;
	}

	public void setQuestion_all(String question_all) {
		this.question_all = question_all;
	}

}
