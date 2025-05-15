package com.example.webtemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DatabaseEnvTests {

    @Value("${POSTGRES_HOST}")
    private String host;

    @Value("${POSTGRES_PORT}")
    private String port;

    @Value("${POSTGRES_DB}")
    private String database;

    @Value("${POSTGRES_USER}")
    private String user;

    @Value("${POSTGRES_PASSWORD}")
    private String password;

    @Test
    void allEnvVarsAreInjectedAndNotBlank() {
        Map<String, String> envVars = Map.of(
                "POSTGRES_HOST", host,
                "POSTGRES_PORT", port,
                "POSTGRES_DB", database,
                "POSTGRES_USER", user,
                "POSTGRES_PASSWORD", password);
        envVars.forEach(
                (key, value) -> assertFalse(value == null || value.isBlank(), key + " should not be null or blank"));
    }
}