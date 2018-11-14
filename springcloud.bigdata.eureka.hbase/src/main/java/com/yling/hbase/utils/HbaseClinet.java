package com.yling.hbase.utils;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * 构造Hbase的单离客户端
 * 
 * @author zhouzhou
 *
 */
@Component
public class HbaseClinet {

	private Configuration conf;

	@Autowired
	private ConfigConstant configConstant;

	private Connection con;

	public Connection getHbaseClinet() {
		conf = getHbaseConfiguration();
		try {
			con = ConnectionFactory.createConnection(conf);// 获得连接对象
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}

	public Configuration getHbaseConfiguration() {
		conf = HBaseConfiguration.create(); // 获得配制文件对象
		conf.set("hbase.zookeeper.quorum", configConstant.getHbaseHost());
		// 设置zookeeper连接端口，默认2181
		conf.set("hbase.zookeeper.property.clientPort", configConstant.getHbasePort());
		// 设置zookeeper的hbase的根目录
		conf.set("zookeeper.znode.parent", configConstant.getHbasePath());
		conf.set("hbase.client.pause", "50");
		// 设置客户端的 重试次数
		conf.set("hbase.client.retries.number", "3");
		// 设置客户端的 rpc连接超时时间
		conf.set("hbase.rpc.timeout", "2000");
		// 设置客户端rpc操作时间
		conf.set("hbase.client.operation.timeout", "3000");
		conf.set("hbase.client.scanner.timeout.period", "10000");
		return conf;
	}

	public void closeHbaseClinet() {
		try {
			con.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
