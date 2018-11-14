package com.yling.hanlp.pojo;



import java.util.Date;

/**
 * @Author:zhouzhou
 * @Description:
 * @Date:2018/5/10 11:21
 * @Modified:
 */
public class AgriConsultHotWord {

	public String Id;

	public Date createAt;

	public Integer sort;

	public Integer status;

	public Date updateAt;

	public Integer count;
	
	public Integer version;
	
	public String agriConsultId;

	public String getAgriConsultId() {
		return agriConsultId;
	}

	public void setAgriConsultId(String agriConsultId) {
		this.agriConsultId = agriConsultId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date date;

	public String hot_word;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHot_word() {
		return hot_word;
	}

	public void setHot_word(String hot_word) {
		this.hot_word = hot_word;
	}

}
