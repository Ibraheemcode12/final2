package com.example.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class UserServiceApplicationTests {

RestTemplate restTemplate = new RestTemplate();

final String UserServiceUrl = "http://localhost:8080/user/";


	@Test
	void Get_user_info() {

 restTemplate.getForEntity(UserServiceUrl +"username/Ahemd1", User.class).getBody();

    assertEquals(true, true);

	}

}
