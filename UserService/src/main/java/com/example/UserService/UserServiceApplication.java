package com.example.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserServiceApplication {
//  public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
 