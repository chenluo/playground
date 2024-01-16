package com.chenluo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class PGTestBase {

    @Container
    static final PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer(DockerImageName.parse("postgres:14"));

    @BeforeAll
    static void setup() {}

    @AfterAll
    static void teardown() {}

    @DynamicPropertySource
    static void ctrProp(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add(
                "spring.datasource.driverClassName",
                () -> postgreSQLContainer.getDriverClassName());
        registry.add("spring.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgreSQLContainer.getPassword());
        registry.add("spring.datasource.max-pool-size", () -> 5);
        registry.add("spring.datasource.flyway.enabled", () -> "true");
        registry.add("spring.flyway.locations", () -> "classpath:/db-pg/migration");
    }
}
