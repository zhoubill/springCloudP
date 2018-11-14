package com.yling.hanlp.dao;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yling.hanlp.pojo.AgriConsult;
import com.yling.hanlp.pojo.AgriConsultHotWord;



@Repository
public class AgriConsultDao {

	@Autowired
	@Qualifier("mysqlJdbcTemplate")
	private JdbcTemplate MysqljdbcTemplate;

	/**
	 * 根据ID获取农高会咨询的问题文字描述
	 * 
	 * @param Id
	 * @return
	 */
	public List<AgriConsult> getAllAgriConsultById(String Id) {

		String sql = "SELECT id as Id,question_all as question_all, deal_status as deal_status, date as date FROM caf_consult_question where id = ? ";
		RowMapper<AgriConsult> rowMapper = new BeanPropertyRowMapper<>(AgriConsult.class);
		List<AgriConsult> agriConsultList = MysqljdbcTemplate.query(sql, rowMapper, Id);
		return agriConsultList;

	}

	/**
	 * 批量插入农高会咨询问题分词的结果
	 * 
	 * @param agriConsultHotWordList
	 */
	public void bathInsertAgriConsultHotWord(List<AgriConsultHotWord> agriConsultHotWordList) {

		String sql = "INSERT INTO  caf_consult_hot_word(id, createAt, sort,status,updateAt, version, count, date,hot_word,caf_consult_question_id) VALUES(?,?,?,?,?,?,?,?,?,?)";
		List<Object[]> batchArgs = new ArrayList<>();
		for (AgriConsultHotWord agriRecord : agriConsultHotWordList) {
			batchArgs.add(new Object[] { UUID.randomUUID().toString(), agriRecord.getCreateAt(), agriRecord.getSort(),
					agriRecord.getStatus(), agriRecord.getUpdateAt(), agriRecord.getVersion(), agriRecord.getCount(),
					agriRecord.getDate(), agriRecord.getHot_word(), agriRecord.getAgriConsultId() });
		}
		MysqljdbcTemplate.batchUpdate(sql, batchArgs);
	}

	/**
	 * 根据id修改农高会咨询分词处理状态
	 * 
	 * @param id
	 * @param status
	 */
	public void updateAgriConsult(String id, Integer status) {
		String sql = "update caf_consult_question set deal_status=? where id=?";
		int rows = MysqljdbcTemplate.update(sql, status, id);
	}
	
	
	/**
	 * 根据id修改农高会咨询分词处理状态
	 * 
	 * @param id
	 * @param status
	 */
	public void deleteAgriConsultWord(Date date) {
		String sql = "delete from caf_consult_hot_word where date = ? ";
		int rows = MysqljdbcTemplate.update(sql, date);
	}

}
