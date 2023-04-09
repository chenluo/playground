package com.chenluo;

import com.chenluo.service.ZKConfiguration;
import com.chenluo.service.ZKManager;
import com.chenluo.service.ZKManagerImpl;
import org.apache.zookeeper.KeeperException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class Application {
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

    //    @Bean
    public ZKManager zkManager(ZKConfiguration zkConfiguration)
            throws InterruptedException, IOException, KeeperException {
        return new ZKManagerImpl(zkConfiguration);
    }
}
