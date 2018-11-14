package com.yling.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.yling.*")
public class EsClinetApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsClinetApplication.class, args);
	}
}
