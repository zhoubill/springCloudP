package com.yling.neo4j.utils;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;



public class Neo4jClinet {

	protected Driver driver;

	private static Neo4jClinet instance = null;

	private Neo4jClinet() {
		driver = GraphDatabase.driver(ConfigUtil.get("neo4j.properties", "neo4j.url"),
				AuthTokens.basic(ConfigUtil.get("neo4j.properties", "neo4j.user"),
						ConfigUtil.get("neo4j.properties", "neo4j.password")));
	}

	public static Neo4jClinet getInstance() {
		if (instance == null) {
			synchronized (Neo4jClinet.class) {
				if (instance == null) {// 2
					instance = new Neo4jClinet();
				}
			}
		}
		return instance;
	}
	
	public Driver getNeo4JDriver() {
		return driver;
	}

	public void closeNeo4jDriver() {
		try {
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
