package com.assignment.tuum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssignmentApplicationTests {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost:";

	public static RestTemplate restTemplate;

	@BeforeAll
	public static void init()
	{
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setBaseUrl()
	{
		baseUrl = baseUrl.concat(port+"").concat("/api");
	}

	@Test
	void contextLoads() {


	}



}
