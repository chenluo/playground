package com.chenluo.config;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import javax.sql.DataSource;

// @Configuration
public class JpaConfig {
    //    @Bean
    //    @ConfigurationProperties(prefix = "spring.datasource")
    DataSource dataSource(DataSourceProperties dataSourceProperties) {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(dataSourceProperties.getDriverClassName());
        ds.setJdbcUrl(dataSourceProperties.getUrl());
        ds.setUsername(dataSourceProperties.getUsername());
        ds.setPassword(dataSourceProperties.getPassword());
        ds.setMaximumPoolSize(32);
        return ds;
    }
}
