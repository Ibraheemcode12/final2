package com.example.PostServicee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PostServiceeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostServiceeApplication.class, args);
	}

}
