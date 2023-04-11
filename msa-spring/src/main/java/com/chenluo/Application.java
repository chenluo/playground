package com.chenluo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.chenluo.*"})
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(4);
        return threadPoolTaskScheduler;
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
