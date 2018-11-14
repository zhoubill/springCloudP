package com.yling.recommend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.yling.*")
public class RecommendClientApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RecommendClientApplication.class, args);
	}

}
