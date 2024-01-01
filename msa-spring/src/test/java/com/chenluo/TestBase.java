package com.chenluo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class TestBase {

    @Container
    static final MySQLContainer mySQLContainer =
            new MySQLContainer<>(DockerImageName.parse("mysql:8.0-debian"));

    @BeforeAll
    static void setup() {}

    @AfterAll
    static void teardown() {}

    @DynamicPropertySource
    static void ctrProp(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> mySQLContainer.getJdbcUrl());
        registry.add(
                "spring.datasource.driverClassName", () -> mySQLContainer.getDriverClassName());
        registry.add("spring.datasource.username", () -> mySQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> mySQLContainer.getPassword());
        registry.add("spring.datasource.max-pool-size", () -> 5);
        registry.add("spring.datasource.flyway.enabled", () -> "true");
        registry.add("spring.datasource.flyway.locations", () -> "classpath:/migration");
    }
}
