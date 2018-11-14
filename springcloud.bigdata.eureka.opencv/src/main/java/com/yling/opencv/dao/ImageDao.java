package com.yling.opencv.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yling.opencv.pojo.ImageVO;

@Repository
public class ImageDao {

	@Autowired
	private JdbcTemplate BigDataMysqljdbcTemplate;

	/**
	 * 根据图片ID获取图片的描述信息
	 * 
	 * @param imageId
	 * @return
	 */
	public List<ImageVO> getOneImageById(String imageId) {
		String sql = "SELECT id as imageId,imageDesc as imageDesc, imageClassification as imageClassification, imageName as imageName FROM image where id = ? ";
		RowMapper<ImageVO> rowMapper = new BeanPropertyRowMapper<>(ImageVO.class);
		List<ImageVO> imageList = BigDataMysqljdbcTemplate.query(sql, rowMapper, imageId);
		return imageList;
	}

	/**
	 * 根据图片名称获取图片的描述信息
	 * 
	 * @param imageId
	 * @return
	 */
	public List<ImageVO> getOneImageByName(String imageName) {
		String sql = "SELECT id as imageId,imageDesc as imageDesc, imageClassification as imageClassification, imageName as imageName FROM image where imageName = ? ";
		RowMapper<ImageVO> rowMapper = new BeanPropertyRowMapper<>(ImageVO.class);
		List<ImageVO> imageList = BigDataMysqljdbcTemplate.query(sql, rowMapper, imageName);
		return imageList;
	}

}
