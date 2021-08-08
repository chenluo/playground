package com.chenluo.service;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.stereotype.Service;

@Service
public class DBUtil {


    private final DataSource dataSource;

    public DBUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
