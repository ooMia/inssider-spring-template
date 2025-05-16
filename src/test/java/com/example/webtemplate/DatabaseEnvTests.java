package com.example.webtemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
class DatabaseEnvTests {

    @Test
    void contextLoads() {
        // Temporal test to turn off lint warnings
    }

    abstract static class BaseProfileTest {
        @Autowired
        Environment env;

        @Value("${spring.jpa.hibernate.ddl-auto}")
        String ddlAuto;

        boolean isDev() {
            return Arrays.asList(env.getActiveProfiles()).contains("dev");
        }

        boolean isProd() {
            return Arrays.asList(env.getActiveProfiles()).contains("prod");
        }
    }

    @Nested
    @ActiveProfiles("prod")
    class ProdProfileTest extends BaseProfileTest {
        @Test
        void test() {
            assertFalse(isDev(), "prod 활성화 시 dev는 활성화되어선 안 됩니다.");
            assertFalse("create-drop".equals(ddlAuto) || "update".equals(ddlAuto),
                    "prod만 활성화 시 ddlAuto는 'create-drop' 또는 'update'가 아니어야 합니다. 현재: " + ddlAuto);
        }
    }

    @Nested
    @ActiveProfiles("dev")
    class DevProfileTest extends BaseProfileTest {
        @Test
        void test() {
            assertFalse(isProd(), "dev 활성화 시 prod는 활성화되어선 안 됩니다.");
            assertTrue("create-drop".equals(ddlAuto) || "update".equals(ddlAuto),
                    "dev만 활성화 시 ddlAuto는 'create-drop' 또는 'update'여야 합니다. 현재: " + ddlAuto);
        }
    }

    @Nested
    class NonProdProfileTest extends BaseProfileTest {
        @Test
        void test() {
            assertFalse(isProd(), "프로필 미지정 시 prod는 활성화되어선 안 됩니다.");
            assertTrue("create-drop".equals(ddlAuto) || "update".equals(ddlAuto),
                    "프로필 미지정 시 ddlAuto는 'create-drop' 또는 'update'여야 합니다. 현재: " + ddlAuto);
        }
    }
}