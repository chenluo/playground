package com.chenluo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@Import({JpaConfig.class, SentryConfig.class, SecurityConfig.class})
public class AllConfig {
    //    @Bean
    //    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
    //        httpSecurity.authorizeHttpRequests((requests) -> {
    //            requests.requestMatchers("/", "/main").permitAll()
    //                    .anyRequest().authenticated();
    //        });
    //    }
}