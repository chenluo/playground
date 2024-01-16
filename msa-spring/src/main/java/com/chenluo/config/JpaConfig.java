package com.chenluo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

// @Configuration
public class JpaConfig {
    //    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    //    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-pg")
    DataSource dataSourcePg() {
        return DataSourceBuilder.create().build();
    }
}
