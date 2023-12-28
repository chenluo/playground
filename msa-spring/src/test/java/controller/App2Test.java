package controller;

import com.chenluo.Application;
import com.chenluo.service.StatefulService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Disabled
public class App2Test {

    private final StatefulService service;

    @Autowired
    public App2Test(StatefulService service) {
        this.service = service;
    }

    @Test
    public void test() {
        service.increase();
    }

    @Test
    public void get() {
        System.out.println(service.get());
    }
}
