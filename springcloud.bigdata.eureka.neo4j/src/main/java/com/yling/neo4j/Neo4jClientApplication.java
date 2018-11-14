package com.yling.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.yling.*")
public class Neo4jClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(Neo4jClientApplication.class, args);
	}
}
