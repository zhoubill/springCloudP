package com.yling.hbase.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class ConfigConstant {

	@Value("${hbase.host}")
	public String hbaseHost;

	@Value("${hbase.port}")
	public String hbasePort;

	@Value("${hbase.path}")
	public String hbasePath;

	public String getHbaseHost() {
		return hbaseHost;
	}

	public void setHbaseHost(String hbaseHost) {
		this.hbaseHost = hbaseHost;
	}

	public String getHbasePort() {
		return hbasePort;
	}

	public void setHbasePort(String hbasePort) {
		this.hbasePort = hbasePort;
	}

	public String getHbasePath() {
		return hbasePath;
	}

	public void setHbasePath(String hbasePath) {
		this.hbasePath = hbasePath;
	}

}
