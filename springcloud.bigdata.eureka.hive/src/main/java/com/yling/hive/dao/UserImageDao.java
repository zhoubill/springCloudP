package com.yling.hive.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yling.hive.pojo.UserImageAge;
import com.yling.hive.pojo.UserImageArea;
import com.yling.hive.pojo.UserImageSex;

import java.util.List;

/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/8/19 9:18
 * @Modified:
 */

@Repository
public class UserImageDao {

    @Autowired
    @Qualifier("hiveJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<UserImageArea> queryUserImageArea(){
        String sql = "SELECT provincename as provinceName,totalnum as total FROM user_image_area_info";
        RowMapper<UserImageArea> rowMapper = new BeanPropertyRowMapper<>(UserImageArea.class);
        List<UserImageArea> userImageAreaList = jdbcTemplate.query(sql, rowMapper);
        return userImageAreaList;
    }

    public List<UserImageSex> queryUserImageSex(){
        String sql = "SELECT sex as sex,totalnum as total FROM user_image_sex_info";
        RowMapper<UserImageSex> rowMapper = new BeanPropertyRowMapper<>(UserImageSex.class);
        List<UserImageSex> userImageAreaList = jdbcTemplate.query(sql, rowMapper);
        return userImageAreaList;
    }

    public List<UserImageAge> queryUserImageAge(){
        String sql = "SELECT age as age,totalnum as total FROM user_image_age_info";
        RowMapper<UserImageAge> rowMapper = new BeanPropertyRowMapper<>(UserImageAge.class);
        List<UserImageAge> userImageAgeList = jdbcTemplate.query(sql, rowMapper);
        return userImageAgeList;
    }
}
