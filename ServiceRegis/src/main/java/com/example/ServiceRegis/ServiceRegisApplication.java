package com.example.ServiceRegis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegisApplication.class, args);
		
	}
	
	
}
