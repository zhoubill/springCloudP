package com.yling.hanlp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

	@Bean(name = "bigdataDataSource")
    @Qualifier("bigdataDataSource")
    @ConfigurationProperties(prefix="spring.datasource.bigdata")
    public DataSource primaryDataSource() {
            return DataSourceBuilder.create().build();
    }


    @Bean(name = "mysqlDataSource")
    @Qualifier("mysqlyDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.mysql")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();      
    }


    @Bean(name="bigdataJdbcTemplate")
    public JdbcTemplate bigdataJdbcTemplate (
        @Qualifier("bigdataDataSource")  DataSource dataSource ) {

        return new JdbcTemplate(dataSource);
    }

    @Bean(name="mysqlJdbcTemplate")
    public JdbcTemplate  mysqlJdbcTemplate(
            @Qualifier("mysqlyDataSource") DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }

}
