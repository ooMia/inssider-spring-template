package com.example.webtemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class DevEnvironmentTests {

    @Autowired
    Environment env;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    String ddlAuto;

    @Value("${spring.datasource.url}")
    String dbUrl;

    @Value("${spring.datasource.username}")
    String dbUsername;

    @Value("${spring.datasource.password}")
    String dbPassword;

    @Value("${server.port}")
    String serverPort;

    @Test
    void ensuresIdempotency() {

        assertEquals("create-drop", ddlAuto);
        assertEquals("jdbc:postgresql://localhost:5432/dev", dbUrl);
        assertEquals("user", dbUsername);
        assertEquals("user", dbPassword);
        assertEquals("8080", serverPort);

        assertEquals("localhost", env.getProperty("POSTGRES_HOST"));
        assertEquals("dev", env.getProperty("POSTGRES_DB"));
        assertEquals("5432", env.getProperty("POSTGRES_PORT"));
        assertEquals("user", env.getProperty("POSTGRES_USER"));
        assertEquals("user", env.getProperty("POSTGRES_PASSWORD"));
        assertEquals("8080", env.getProperty("APP_PORT"));
    }
}