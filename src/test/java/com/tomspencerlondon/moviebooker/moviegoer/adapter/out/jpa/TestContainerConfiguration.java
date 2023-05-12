package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@Testcontainers(disabledWithoutDocker = true)
public abstract class TestContainerConfiguration {
    static final MySQLContainer mysqlContainer;

    static {
        mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"))
                .withDatabaseName("test")
                .withUsername("duke")
                .withPassword("s3cret");
        mysqlContainer.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
    }
}
