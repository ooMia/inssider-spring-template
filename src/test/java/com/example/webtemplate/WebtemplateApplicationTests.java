package com.example.webtemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebtemplateApplicationTests {

	@Autowired
	BuildProperties buildProperties;

	@Test
	void contextLoads() {
		// Check application context from application.yaml
		assertEquals("webtemplate", buildProperties.getName());
		assertEquals("0.0.1-SNAPSHOT", buildProperties.getVersion());
		assertEquals("com.example", buildProperties.getGroup());
	}
}
