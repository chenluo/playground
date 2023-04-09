package com.chenluo;

import com.chenluo.service.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.chenluo.*"})
@EnableScheduling
public class Application {
    private final MyService myService;

    public Application(MyService myService) {
        this.myService = myService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    //    @Bean
    //    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    //        return args -> {
    //
    //            System.out.println("Let's inspect the beans provided by Spring Boot:");
    //
    //            String[] beanNames = ctx.getBeanDefinitionNames();
    //            Arrays.sort(beanNames);
    //            for (String beanName : beanNames) {
    //                System.out.println(beanName);
    //            }
    //
    //        };
    //    }
}
