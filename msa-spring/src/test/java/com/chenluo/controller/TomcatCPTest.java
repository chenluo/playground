package com.chenluo.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.chenluo.TestBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest()
public class TomcatCPTest extends TestBase {

    @Autowired private DataSource dataSource;

    //    @Test
    public void givenTomcatConnectionPoolInstance_whenCheckedPoolClassName_thenCorrect() {
        assertThat(dataSource.getClass().getName())
                .isEqualTo("org.apache.tomcat.jdbc.pool.DataSource");
    }
}
