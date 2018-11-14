package com.yling.es.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EsClinet {

	@Autowired
	public ConfigConstant configConstant;

	protected TransportClient client;

	protected RestClient restClient;

	public TransportClient getEsclinet() {
		Settings settings = Settings.builder().put("cluster.name", configConstant.getClusterName()).build();
		try {
			client = new PreBuiltTransportClient(settings).addTransportAddress(
					new InetSocketTransportAddress(InetAddress.getByName(configConstant.getEshost1()), 9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
	}

	public RestClient getRestClinet() {
		restClient = RestClient.builder(new HttpHost(configConstant.getEshost1(), 9200, "http"),
				new HttpHost(configConstant.getEshost2(), 9200, "http"),
				new HttpHost(configConstant.getEshost3(), 9200, "http")).build();
		return restClient;
	}

	public void closeEsClinet() {
		client.close();

	}

	public void closeEsRestClinet() {
		try {
			restClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
