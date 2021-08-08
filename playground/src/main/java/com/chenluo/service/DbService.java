package com.chenluo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class DbService {

    private final DBUtil dbUtil;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DbService(DBUtil dbUtil, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.dbUtil = dbUtil;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    public void simpleTrans() throws SQLException {

        StopWatch stopWatch = new StopWatch();
        for (int i = 0; i < 60; i++) {
            System.out.println(String.valueOf(i));
            if (i > 50) {
                throw new RuntimeException("manual exception");
            }
            Connection con = null;
            try {
                stopWatch.start("getConnection"+i);
                con = dbUtil.getDataSource().getConnection();
                stopWatch.stop();
                System.out.println(stopWatch.getLastTaskInfo().getTaskName()+":"+stopWatch.getLastTaskTimeMillis());
                stopWatch.start(String.valueOf(i));
                Statement st = con.createStatement();

                int effectedRows = st.executeUpdate(String.format("insert into testTbl(v) values(%d)", i));
                st.close();
                stopWatch.stop();
                System.out.println(stopWatch.getLastTaskTimeMillis());
            } finally {
                if (con != null) try {
                    con.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    @Transactional
    public void tran(int loopCount) {
        for (int i = 0; i < loopCount; i++) {
            if (i > 50) {
                throw new RuntimeException("manual exception.");
            }
            namedParameterJdbcTemplate.getJdbcOperations().update("insert into testTbl(v) values(?)", String.valueOf(i));
        }
    }
}
