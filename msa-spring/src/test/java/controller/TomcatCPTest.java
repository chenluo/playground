package controller;

import com.chenluo.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = Application.class)
public class TomcatCPTest {

    @Autowired
    private DataSource dataSource;

    //    @Test
    public void givenTomcatConnectionPoolInstance_whenCheckedPoolClassName_thenCorrect() {
        assertThat(dataSource.getClass().getName()).isEqualTo(
                "org.apache.tomcat.jdbc.pool.DataSource");
    }
}
