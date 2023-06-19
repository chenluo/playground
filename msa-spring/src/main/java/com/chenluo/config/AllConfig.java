package com.chenluo.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableWebSecurity
@EnableCaching
@Import({JpaConfig.class, SecurityConfig.class})
public class AllConfig {
    //    @Bean
    //    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
    //        httpSecurity.authorizeHttpRequests((requests) -> {
    //            requests.requestMatchers("/", "/main").permitAll()
    //                    .anyRequest().authenticated();
    //        });
    //    }
    @Bean
    public DefaultErrorHandler errorHandler() {
        BackOff fixedBackOff = new FixedBackOff(1000, 10);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, e) -> {
            // logic to execute when all the retry attemps are exhausted
        }, fixedBackOff);
        //        errorHandler.addRetryableExceptions(SocketTimeoutException.class);
        //        errorHandler.addNotRetryableExceptions(NullPointerException.class);
        return errorHandler;
    }
}
