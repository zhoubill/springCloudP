package com.yling.es.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigConstant {
	
	@Value("${es.cluster.name}")
	public  String clusterName;
	
	@Value("${es.host1}")
	public  String eshost1;
	
	@Value("${es.host2}")
	public  String eshost2;
	
	@Value("${es.host3}")
	public  String eshost3;
	
	@Value("${es.index}")
	public  String esIndex;
	
	@Value("${es.type}")
	public  String esType;
	
	@Value("${es.expert.index}")
	public  String esExpertIndex;
	
	@Value("${es.expert.type}")
	public  String esExpertType;
	
	@Value("${es.pest.index}")
	public  String esPestIndex;
	
	@Value("${es.pest.type}")
	public  String esPestType;
	
	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getEshost1() {
		return eshost1;
	}

	public void setEshost1(String eshost1) {
		this.eshost1 = eshost1;
	}

	public String getEshost2() {
		return eshost2;
	}

	public void setEshost2(String eshost2) {
		this.eshost2 = eshost2;
	}

	public String getEshost3() {
		return eshost3;
	}

	public void setEshost3(String eshost3) {
		this.eshost3 = eshost3;
	}

	public String getEsIndex() {
		return esIndex;
	}

	public void setEsIndex(String esIndex) {
		this.esIndex = esIndex;
	}

	public String getEsType() {
		return esType;
	}

	public void setEsType(String esType) {
		this.esType = esType;
	}

	public String getEsExpertIndex() {
		return esExpertIndex;
	}

	public void setEsExpertIndex(String esExpertIndex) {
		this.esExpertIndex = esExpertIndex;
	}

	public String getEsExpertType() {
		return esExpertType;
	}

	public void setEsExpertType(String esExpertType) {
		this.esExpertType = esExpertType;
	}

	public String getEsPestIndex() {
		return esPestIndex;
	}

	public void setEsPestIndex(String esPestIndex) {
		this.esPestIndex = esPestIndex;
	}

	public String getEsPestType() {
		return esPestType;
	}

	public void setEsPestType(String esPestType) {
		this.esPestType = esPestType;
	}

}
