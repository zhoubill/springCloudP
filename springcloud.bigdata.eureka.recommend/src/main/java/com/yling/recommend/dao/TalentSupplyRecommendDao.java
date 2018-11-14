package com.yling.recommend.dao;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yling.recommend.pojo.RecommendVO;


@Repository
public class TalentSupplyRecommendDao {

	@Autowired
	private JdbcTemplate BigDataMysqljdbcTemplate;

	/**
	 * 根据人才供需的ID和类型获取相应的推荐的ID
	 * @param sourceId
	 * @param type
	 * @return
	 */
	public List<RecommendVO> getTalentSupplyRecommendByIdAndType(String sourceId, String type) {
		String sql = "SELECT sourceID,recommendID, type, score as match_degree FROM recomend_talent_supply where sourceID = ?  and type = ?";
		RowMapper<RecommendVO> rowMapper = new BeanPropertyRowMapper<>(RecommendVO.class);
		List<RecommendVO> recommendList = BigDataMysqljdbcTemplate.query(sql, rowMapper, sourceId, type);
		return recommendList;
	}

}
