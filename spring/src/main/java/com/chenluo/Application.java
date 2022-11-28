package com.chenluo;

import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.chenluo.*"})
@EnableScheduling
public class Application {
    Logger logger = LoggerFactory.getLogger(Application.class);
    private int scheduledCount = 0;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //    @Scheduled(cron = "*/1 * * * * *")
    public void heartbeat() {
        scheduledCount++;
        logger.error("scheduled {} times.", scheduledCount);
        Sentry.captureMessage("[active] scheduled " + scheduledCount + " times.");
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void allocMem() {
        scheduledCount++;
        logger.info("scheduled {} times.", scheduledCount);
        for (int i = 0; i < 1000; i++) {
            int[] ints = new int[1_000];
        }
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
