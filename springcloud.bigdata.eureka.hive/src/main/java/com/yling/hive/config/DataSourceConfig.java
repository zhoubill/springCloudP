package com.yling.hive.config;

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

	@Bean(name = "hiveDataSource")
	@Qualifier(value = "hiveDataSource")
	@Primary
	@ConfigurationProperties(prefix = "c3p0")
	public DataSource dataSource() {
		return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
	}
	
	
    @Bean(name="hiveJdbcTemplate")
    public JdbcTemplate bigdataJdbcTemplate (
        @Qualifier("hiveDataSource")  DataSource dataSource ) {
        return new JdbcTemplate(dataSource);
    }

}
